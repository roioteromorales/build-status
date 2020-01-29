package com.roisoftstudio.buildstatus.data;

import com.roisoftstudio.buildstatus.data.dto.DroneRepo;
import com.roisoftstudio.buildstatus.data.dto.GithubCompare;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "github", url = "${github.url}")
public interface GithubRepository {

  @GetMapping("repos/{organization}/{repo}/compare/{compare}...{base}")
  GithubCompare compareTags(@PathVariable String organization, @PathVariable String repo, @PathVariable String compare,
      @PathVariable String base, @RequestParam("access_token") String token);

  @GetMapping("user/repos")
  List<DroneRepo> getRepos(@RequestHeader("Authorization") String token);

}
