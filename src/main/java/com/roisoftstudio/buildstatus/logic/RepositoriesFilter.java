package com.roisoftstudio.buildstatus.logic;

import static java.util.Arrays.asList;

import com.roisoftstudio.buildstatus.data.dto.DroneRepo;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class RepositoriesFilter {

  @Value("${blacklist}")
  private String[] blacklistArray;
  private List<String> blacklist;
  private Map<String, List<String>> teamsToRepos = new HashMap<>();

  public RepositoriesFilter() {
    teamsToRepos.put("core", asList(
        "red-api-proxy",
        "red-balances",
        "red-balance-management",
        "red-dummy",
        "red-eagle-eye-proxy",
        "red-gatlin",
        "red-member",
        "red-monitor-proxy",
        "red-onboarding",
        "red-smoke",
        "red-statement",
        "red-txn-ingestion",
        "red-txn-processor",
        "red-user-identity",
        "red-vaa-file-ingestor",
        "red-vaa-hist-txn-ingestion",
        "red-vaa-member-ingestion",
        "red-vaa-txn-ingestion"
    ));
    teamsToRepos.put("api", asList(
        "red-api-proxy",
        "red-balance-management",
        "red-balances",
        "red-eagle-eye-proxy",
        "red-onboarding",
        "red-dummy",
        "red-statement",
        "red-member",
        "red-txn-processor",
        "red-outbound-proxy",
        "red-user-identity"
    ));
  }

  @PostConstruct
  public void initializeFile() {
    blacklist = asList(blacklistArray);
    log.info("BlackList repos: {}", blacklist);
  }

  public boolean isBlacklisted(DroneRepo droneRepo) {
    return !blacklist.contains(droneRepo.getName());
  }

  public boolean isInTeam(String team, DroneRepo droneRepo) {
    if (StringUtils.isEmpty(team)) {
      return true;
    }

    List<String> teamRepos = teamsToRepos.getOrDefault(team, Collections.emptyList());
    return teamRepos.contains(droneRepo.getName());
  }
}
