package com.roisoftstudio.buildstatus.presentation.api;

import static com.roisoftstudio.buildstatus.logic.GithubService.DEV;
import static com.roisoftstudio.buildstatus.logic.GithubService.STAGING;

import com.roisoftstudio.buildstatus.logic.GithubService;
import com.roisoftstudio.buildstatus.logic.model.VersionsDiff;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
public class GithubApiController {

  private final GithubService githubService;

//  @GetMapping("/repo/{repo}/versions")
//  public VersionsDiff getVersions(@PathVariable String repo) {
//    return githubService.getVersions(repo, DEV, STAGING);
//  }

}
