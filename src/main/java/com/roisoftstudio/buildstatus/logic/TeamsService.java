package com.roisoftstudio.buildstatus.logic;

import com.roisoftstudio.buildstatus.data.TeamsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamsService {

  private final TeamsRepository teamsRepository;

  public List<String> addReposToTeam(String team, List<String> repos) {
    return teamsRepository.addReposToTeam(team, repos);
  }

  public List<String> deleteReposFromTeam(String team, List<String> repos) {
    return teamsRepository.deleteReposFromTeam(team, repos);
  }

  public List<String> getTeamRepos(String team) {
    return teamsRepository.getTeamRepos(team);
  }
}
