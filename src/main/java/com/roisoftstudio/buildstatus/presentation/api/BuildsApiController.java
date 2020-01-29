package com.roisoftstudio.buildstatus.presentation.api;

import com.roisoftstudio.buildstatus.data.dto.DroneBuild;
import com.roisoftstudio.buildstatus.data.dto.DroneRepo;
import com.roisoftstudio.buildstatus.logic.DroneService;
import com.roisoftstudio.buildstatus.logic.helpers.ShellResponse;
import com.roisoftstudio.buildstatus.presentation.api.dto.PromoteBuildRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
public class BuildsApiController {

  private final DroneService droneService;

  @GetMapping("/builds")
  public List<DroneBuild> getBuilds() {
    return droneService.getBuilds("red-balance-management");
  }

  @GetMapping("/repositories")
  public List<DroneRepo> getRepositories() {
    return droneService.getRepositories();
  }

  @PostMapping("/builds/promote")
  public ShellResponse promoteBuild(@RequestBody PromoteBuildRequest promoteBuildRequest) {
    log.info("called promote with: {}", promoteBuildRequest);
    ShellResponse shellResponse = droneService
        .promoteBuild(promoteBuildRequest.getRepo(), promoteBuildRequest.getBuildNumber(), promoteBuildRequest.getEnvironment());
    log.info("promote response: {}", shellResponse);
    return shellResponse;
  }
}
