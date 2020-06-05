package com.roisoftstudio.buildstatus.logic;

import static java.util.stream.Collectors.toList;

import com.roisoftstudio.buildstatus.data.DroneRepository;
import com.roisoftstudio.buildstatus.data.dto.DroneBuild;
import com.roisoftstudio.buildstatus.data.dto.DroneRepo;
import com.roisoftstudio.buildstatus.data.dto.DroneShortBuild;
import com.roisoftstudio.buildstatus.logic.exception.BuildNotFoundException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DroneService {

  private final DroneRepository droneRepository;
  private final RepositoriesFilter repositoriesFilter;

  @Value("${drone.token}")
  private String token;
  @Value("${drone.url}")
  private String url;
  @Value("${drone.organization}")
  private String organization;
  @Value("${github.repositoryPrefix}")
  private String repoPrefix;

  public List<DroneBuild> getBuilds(String repository) {
    return droneRepository.getBuilds(organization, repository, token);
  }

  public List<DroneRepo> getRepositories(String team) {
    return droneRepository.getRepos(token).stream()
        .filter(droneRepo -> droneRepo.getName().contains(repoPrefix))
        .filter(droneRepo -> repositoriesFilter.isInTeam(team, droneRepo))
        .filter(repositoriesFilter::isBlacklisted)
        .collect(toList());
  }

  public List<String> getRepositoryForTeamWithNamesLike(String team, String search) {
    return getRepositories(team).stream()
        .map(DroneRepo::getName)
        .filter(name -> name.contains(search))
        .collect(toList());
  }

  public DroneBuild promoteBuild(String repo, Integer buildNumber, String environment) {
    return droneRepository.promoteBuild(organization, repo, buildNumber, environment, token);
  }

  public DroneBuild getBuildForCommit(String repository, String commit) {
    return IntStream.rangeClosed(1, 5)
        .parallel()
        .boxed()
        .map(page -> droneRepository.getBuildsForPage(organization, repository, page, token))
        .flatMap(Collection::stream)
        .filter(droneBuild -> droneBuild.getAfter().equalsIgnoreCase(commit))
        .filter(droneBuild -> !droneBuild.getBefore().equalsIgnoreCase(commit))
        .sorted(Comparator.comparing(droneBuild -> droneBuild.getNumber()))
        .findFirst()
        .orElseThrow(() -> new BuildNotFoundException(
            "Could not find the build for that commit, maybe is too old (only checking last 100 builds): " + commit));
  }

  public Map<String, List<DroneShortBuild>> getBuildForPromotion(List<String> repos){
    return repos
        .parallelStream()
        .collect(Collectors.toMap(repo -> repo, repo -> IntStream.rangeClosed(1, 5)
            .parallel()
            .boxed()
            .map(page -> droneRepository.getBuildsForPage(organization, repo, page, token))
            .flatMap(Collection::stream)
            .filter(droneBuild -> droneBuild.getStatus().equalsIgnoreCase("success"))
            .filter(droneBuild -> droneBuild.getEvent().equalsIgnoreCase("push"))
            .filter(droneBuild -> droneBuild.getSource().equalsIgnoreCase("master"))
            .filter(droneBuild -> droneBuild.getTarget().equalsIgnoreCase("master"))
            .limit(10)
            .map(droneBuild -> shortBuildFunction.apply(repo, droneBuild))
            .collect(Collectors.toList())));
  }

  private BiFunction<String, DroneBuild, DroneShortBuild> shortBuildFunction = (repo, droneBuild) -> {
    return DroneShortBuild.builder()
        .number(droneBuild.getNumber())
        .display(droneBuild.getAuthor_name(), droneBuild.getMessage())
        .link(url, organization, repo)
        .build();
  };
}
