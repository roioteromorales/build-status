package com.roisoftstudio.buildstatus.logic;

import com.roisoftstudio.buildstatus.logic.helpers.ShellExecutor;
import com.roisoftstudio.buildstatus.logic.helpers.ShellResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DroneCliCommander {

  private final ShellExecutor shellExecutor;

  public ShellResponse promoteBuild(String organization, String repo, Integer buildNumber, String environment) {
    String promoteCommand = String.format("drone build promote %s/%s %d %s", organization, repo, buildNumber, environment);
    return shellExecutor.executeCommand(promoteCommand);
  }

}
