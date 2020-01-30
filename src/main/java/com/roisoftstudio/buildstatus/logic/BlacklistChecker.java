package com.roisoftstudio.buildstatus.logic;

import static java.util.Arrays.asList;

import com.roisoftstudio.buildstatus.data.dto.DroneRepo;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlacklistChecker {

  @Value("${blacklist}")
  private String[] blacklistArray;
  private List<String> blacklist;

  @PostConstruct
  public void initializeFile() {
    blacklist = asList(blacklistArray);
    log.info("BlackList repos: {}", blacklist);
  }

  public boolean isBlacklisted(DroneRepo droneRepo) {
    return !blacklist.contains(droneRepo.getName());
  }
}
