package com.roisoftstudio.buildstatus.logic;

import static java.util.stream.Collectors.toList;

import com.roisoftstudio.buildstatus.data.GithubRepository;
import com.roisoftstudio.buildstatus.data.dto.DroneBuild;
import com.roisoftstudio.buildstatus.data.dto.GithubCompare;
import com.roisoftstudio.buildstatus.logic.exception.BuildNotFoundException;
import com.roisoftstudio.buildstatus.logic.model.VersionsDiff;
import com.roisoftstudio.buildstatus.presentation.api.dto.Versions;
import feign.FeignException;
import java.util.List;
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
  public static final String PROD = "prod";
  private final GithubRepository githubRepository;
  private final DroneService droneService;

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
    return repos.stream()
        .parallel()
        .map(this::toVersions)
        .collect(toList());
  }

  private Versions toVersions(String repo) {
    return Versions.builder()
        .repo(repo)
        .dev(getVersionsFor(repo, DEV, DEV))
        .staging(getVersionsFor(repo, DEV, STAGING))
        .prod(getVersionsFor(repo, STAGING, PROD))
        .build();
  }

  private VersionsDiff getVersionsFor(String repo, String from, String to) {
    GithubCompare githubCompare;
    try {
      githubCompare = githubRepository.compareTags(githubOrganization, repo, from, to, token);
      DroneBuild droneBuild = droneService.getBuildForCommit(repo, githubCompare.getMerge_base_commit().getSha());
      return VersionsDiff.builder()
          .statusMessage(getMessage(githubCompare, from, to))
          .status(githubCompare.getStatus())
          .droneBuildStatus(droneBuild.getStatus())
          .droneBuildLink(droneUrl + "/" + organization + "/" + repo + "/" + droneBuild.getNumber())
          .droneBuildNumber(droneBuild.getNumber())
          .commitMessage(githubCompare.getMerge_base_commit().getCommit().getMessage())
          .commitHash(githubCompare.getMerge_base_commit().getSha())
          .commitLink(githubCompare.getMerge_base_commit().getHtml_url())
          .author(githubCompare.getMerge_base_commit().getCommit().getAuthor().getName())
          .datetime(githubCompare.getMerge_base_commit().getCommit().getAuthor().getDate())
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
    return from + " is " + githubCompare.getStatus() + " of " + to + " by " + getDiff(githubCompare) + " commits.";
  }

  private Integer getDiff(GithubCompare githubCompare) {
    switch (githubCompare.getStatus()) {
      case "ahead":
        return githubCompare.getAhead_by();
      case "behind":
        return githubCompare.getBehind_by();
      case "identical":
        return 0;
      default:
        throw new RuntimeException("Not implemented status: " + githubCompare.getStatus());
    }
  }
}
