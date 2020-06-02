package com.roisoftstudio.buildstatus.presentation.web;

import com.roisoftstudio.buildstatus.data.dto.DroneBuild;
import com.roisoftstudio.buildstatus.logic.DroneService;
import com.roisoftstudio.buildstatus.logic.GithubService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BuildsController {


  private final DroneService droneService;
  private final GithubService githubService;

  @GetMapping("/builds/{repo}")
  public String getBuilds(Model model, @PathVariable String repo) {
    model.addAttribute("versions", githubService.getVersions(repo));
    model.addAttribute("builds", getBuilds(repo));
    return "repo";
  }

  @GetMapping("/env-promoter")
  public String eventPromoterForRepo(Model model, @RequestParam(defaultValue = "") String search,
      @RequestParam(defaultValue = "") String team) {
    model.addAttribute("versions", githubService.getVersions(droneService.getRepositoryForTeamWithNamesLike(team, search)));
    return "env-promoter";
  }

  @GetMapping("/build-promoter")
  public String eventBuildPromoterForRepo(Model model, @RequestParam(defaultValue = "") String search,
      @RequestParam(defaultValue = "") String team) {

    var repos = droneService.getRepositoryForTeamWithNamesLike(team, search);

    model.addAttribute("versions", githubService.getVersions(repos));
    model.addAttribute("builds", droneService.getBuildForPromotion(repos));
    return "build-promoter";
  }

  @GetMapping("/")
  public String home(Model model, @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "") String team) {
    model.addAttribute("versions", githubService.getVersions(droneService.getRepositoryForTeamWithNamesLike(team, search)));
    return "home";
  }

  private List<DroneBuild> getBuilds(@PathVariable String repo) {
    return droneService.getBuilds(repo);
  }
}
