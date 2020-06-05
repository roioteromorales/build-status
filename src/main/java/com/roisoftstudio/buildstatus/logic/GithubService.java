package com.roisoftstudio.buildstatus.logic;

import static java.util.stream.Collectors.toList;

import com.roisoftstudio.buildstatus.data.GithubRepository;
import com.roisoftstudio.buildstatus.data.dto.DroneBuild;
import com.roisoftstudio.buildstatus.data.dto.GithubCompare;
import com.roisoftstudio.buildstatus.data.dto.GithubCompare.BaseCommit;
import com.roisoftstudio.buildstatus.logic.exception.BuildNotFoundException;
import com.roisoftstudio.buildstatus.logic.helpers.AsyncInvoker;
import com.roisoftstudio.buildstatus.logic.model.VersionsDiff;
import com.roisoftstudio.buildstatus.presentation.api.dto.Versions;
import feign.FeignException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubService {

  public static final String DEV = "dev";
  public static final String STAGING = "staging";
  public static final String PERF = "perf";
  public static final String PROD = "prod";
  private final GithubRepository githubRepository;
  private final DroneService droneService;
  private final AsyncInvoker asyncInvoker;

  @Value("${github.token}")
  private String token;
  @Value("${drone.url}")
  private String droneUrl;
  @Value("${drone.organization}")
  private String organization;
  @Value("${github.organization}")
  private String githubOrganization;

  public Versions getVersions(String repo) {
    return toVersions(repo);
  }

  public List<Versions> getVersions(List<String> repos) {
    return asyncInvoker.invokeAll(repos.stream()
        .parallel()
        .map((Function<String, Callable<Versions>>) repo -> () -> toVersions(repo))
        .collect(toList()));
  }

  private Versions toVersions(String repo) {
    return Versions.builder()
        .repo(repo)
        .dev(getVersionsFor(repo, DEV, DEV, STAGING))
        .staging(getVersionsFor(repo, DEV, STAGING, PROD))
        .prod(getVersionsFor(repo, STAGING, PROD, null))
        .perf(getVersionsFor(repo, PERF, PERF, null))
        .build();
  }

  private VersionsDiff getVersionsFor(String repo, String base, String compare, String promotingEnvironment) {
    try {
      var githubCompare = githubRepository.compareTags(githubOrganization, repo, compare, base, token);
      var baseCommit = githubCompare.getBase_commit();
      var droneBuild = droneService.getBuildForCommit(repo, baseCommit.getSha());
      return VersionsDiff.builder()
          .repo(repo)
          .promotingEnvironment(promotingEnvironment)
          .statusMessage(getMessage(githubCompare, compare, base))
          .status(githubCompare.getStatus())
          .droneBuildStatus(droneBuild.getStatus())
          .droneBuildEvent(droneBuild.getEvent())
          .droneBuildLink(droneUrl + "/" + organization + "/" + repo + "/" + droneBuild.getNumber())
          .droneBuildNumber(droneBuild.getNumber())
          .commitMessage(baseCommit.getCommit().getMessage())
          .diffNumber(getDiff(githubCompare))
          .diffLink(githubCompare.getHtml_url())
          .author(baseCommit.getCommit().getAuthor().getName())
          .datetime(baseCommit.getCommit().getAuthor().getDate())
          .build();
    } catch (FeignException e) {
      log.error("Error retrieving tags for the repository {} with message {}", repo, e.getLocalizedMessage());
      return VersionsDiff.builder()
          .status("warning")
          .statusMessage("Error retrieving tags: Probably the repository doesnt contain tags... ")
          .build();
    } catch (BuildNotFoundException e) {
      log.error("Error retrieving build for repository {} : {}", repo, e.getLocalizedMessage());
      return VersionsDiff.builder()
          .status("warning")
          .statusMessage(e.getMessage())
          .build();
    }
  }

  private String getMessage(GithubCompare githubCompare, String from, String to) {
    if(githubCompare.getStatus().equals("diverged")){
      return to + " and " + from + " have diverged " + githubCompare.getAhead_by() + " /  " + githubCompare.getBehind_by() + " commits";
    }
    return to + " is " + githubCompare.getStatus() + " of " + from + " by " + getDiff(githubCompare) + " commits.";
  }

  private Integer getDiff(GithubCompare githubCompare) {
    switch (githubCompare.getStatus()) {
      case "ahead":
        return githubCompare.getAhead_by();
      case "behind":
        return githubCompare.getBehind_by();
      case "identical":
        return 0;
      case "diverged":
        return githubCompare.getAhead_by() + githubCompare.getBehind_by();
      default:
        throw new RuntimeException("Not implemented status: " + githubCompare.getStatus());
    }
  }
}
