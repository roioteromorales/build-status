package com.roisoftstudio.buildstatus.data;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamsRepositoryTest {

  @Autowired
  private TeamsRepository teamsRepository;

  @Test
  public void canSave() {
    List<String> reposToAdd = asList("repo1", "repo2");

    List<String> repos = teamsRepository.addReposToTeam("team1", reposToAdd);

    assertThat(repos).containsExactlyInAnyOrderElementsOf(reposToAdd);
  }

  @Test
  public void canDelete() {
    List<String> reposToAdd = asList("repo1", "repo2");
    teamsRepository.addReposToTeam("team2", reposToAdd);

    List<String> repos = teamsRepository.deleteReposFromTeam("team2", asList("repo1"));

    assertThat(repos).containsExactly("repo2");
  }

  @Test
  public void canRetrieve() {
    List<String> reposToAdd = asList("repo1", "repo2");
    teamsRepository.addReposToTeam("team1", reposToAdd);

    List<String> repos = teamsRepository.getTeamRepos("team1");

    assertThat(repos).containsExactlyInAnyOrderElementsOf(reposToAdd);
  }
}