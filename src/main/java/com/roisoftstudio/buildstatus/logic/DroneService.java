package com.roisoftstudio.buildstatus.logic;

import static java.util.stream.Collectors.toList;

import com.roisoftstudio.buildstatus.data.DroneRepository;
import com.roisoftstudio.buildstatus.data.dto.DroneBuild;
import com.roisoftstudio.buildstatus.data.dto.DroneRepo;
import com.roisoftstudio.buildstatus.logic.exception.BuildNotFoundException;
import com.roisoftstudio.buildstatus.logic.helpers.ShellResponse;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DroneService {

  private final DroneRepository droneRepository;
  private final DroneCliCommander droneCliCommander;

  @Value("${drone.token}")
  private String token;
  @Value("${drone.organization}")
  private String organization;
  @Value("${github.repositoryPrefix}")
  private String repoPrefix;

  public List<DroneBuild> getBuilds(String repository) {
    return droneRepository.getBuilds(organization, repository, token);
  }

  public List<DroneRepo> getRepositories() {
    return droneRepository.getRepos(token).stream()
        .filter(repository -> repository.getName().contains(repoPrefix))
        .collect(toList());
  }

  public List<String> getRepositoryNames() {
    return getRepositories().stream()
        .map(DroneRepo::getName)
        .collect(toList());
  }

  public ShellResponse promoteBuild(String repo, Integer buildNumber, String environment) {
    return droneCliCommander.promoteBuild(organization, repo, buildNumber, environment);
  }

  public DroneBuild getBuildForCommit(String repository, String commit) {
    return IntStream.rangeClosed(1, 5)
        .parallel()
        .boxed()
        .map(page -> droneRepository.getBuildsForPage(organization, repository, page, token))
        .flatMap(Collection::stream)
        .filter(droneBuild -> droneBuild.getAfter().equalsIgnoreCase(commit))
        .filter(droneBuild -> !droneBuild.getBefore().equalsIgnoreCase(commit))
        .findFirst()
        .orElseThrow(() -> new BuildNotFoundException("Could not find the build for that commit, maybe the branch is too old: " + commit));
  }
}
