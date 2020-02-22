package com.roisoftstudio.buildstatus.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import lombok.RequiredArgsConstructor;
import org.mapdb.DB;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeamsRepository {

  private static final String TEAMS = "teams";

  private final DB database;

  public List<String> addReposToTeam(String team, List<String> reposToAdd) {
    ConcurrentMap map = database.hashMap(TEAMS).createOrOpen();
    Set<String> storedSet = (Set<String>) map.getOrDefault(team, new HashSet<>());
    reposToAdd.forEach(s -> storedSet.add(s));
    map.put(team, storedSet);
    database.commit();
    return new ArrayList<>(storedSet);
  }

  public List<String> deleteReposFromTeam(String team, List<String> reposToDelete) {
    ConcurrentMap map = database.hashMap(TEAMS).createOrOpen();
    Set<String> storedSet = (Set<String>) map.getOrDefault(team, new HashSet<>());
    reposToDelete.forEach(s -> storedSet.remove(s));
    map.put(team, storedSet);
    database.commit();
    return new ArrayList<>(storedSet);
  }

  public List<String> getTeamRepos(String team) {
    ConcurrentMap map = database.hashMap(TEAMS).createOrOpen();
    Set<String> storedSet = (Set<String>) map.getOrDefault(team, new HashSet<>());
    database.commit();
    return new ArrayList<>(storedSet);
  }

}
