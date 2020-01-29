package com.roisoftstudio.buildstatus.logic;

import static java.util.stream.Collectors.toList;

import com.roisoftstudio.buildstatus.data.dto.DroneRepo;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlacklistChecker {

  private static final String FILENAME = "blacklist-repos";
  private List<String> blacklist = new ArrayList<>();

  public BlacklistChecker() {
    try {
      Path path = Paths.get(getClass().getClassLoader().getResource(FILENAME).toURI());
      Stream<String> lines = Files.lines(path);
      blacklist = lines.collect(toList());
      lines.close();
    } catch (URISyntaxException | IOException e) {
      log.error("There was an error reading the blacklist " + FILENAME, e);
    }
  }

  public boolean isBlacklisted(DroneRepo droneRepo) {
    return !blacklist.contains(droneRepo.getName());
  }
}
