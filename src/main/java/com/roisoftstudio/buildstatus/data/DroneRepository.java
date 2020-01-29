package com.roisoftstudio.buildstatus.data;

import com.roisoftstudio.buildstatus.data.dto.DroneBuild;
import com.roisoftstudio.buildstatus.data.dto.DroneRepo;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "drone", url = "${drone.url}")
public interface DroneRepository {

  @GetMapping("/api/repos/{organization}/{repo}/builds")
  List<DroneBuild> getBuilds(@PathVariable String organization, @PathVariable String repo, @RequestHeader("Authorization") String token);

  @GetMapping("/api/repos/{organization}/{repo}/builds")
  List<DroneBuild> getBuildsForPage(@PathVariable String organization, @PathVariable String repo, @RequestParam Integer page,
      @RequestHeader("Authorization") String token);

  @GetMapping("/api/user/repos")
  List<DroneRepo> getRepos(@RequestHeader("Authorization") String token);

  @PostMapping("/api/repos/{organization}/{repo}/builds/{buildNumber}/promote")
  DroneBuild promoteBuild(@PathVariable String organization, @PathVariable String repo, @PathVariable Integer buildNumber,
      @RequestParam String target, @RequestHeader("Authorization") String token);

}
