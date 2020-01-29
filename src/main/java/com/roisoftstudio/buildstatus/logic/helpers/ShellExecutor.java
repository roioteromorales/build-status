package com.roisoftstudio.buildstatus.logic.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShellExecutor {

  public ShellResponse executeCommand(String command) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.command("bash", "-c", command);
    try {
      Process process = processBuilder.start();

      String output = getOutput(process);
      int exitVal = process.waitFor();
      return ShellResponse.builder()
          .status(exitVal)
          .output(output)
          .build();
    } catch (IOException | InterruptedException e) {
      log.error("Error executing the command " + command, e);
      return ShellResponse.builder()
          .status(-1)
          .output("Could not execute the process: " + e.getLocalizedMessage())
          .build();
    }
  }

  private String getOutput(Process process) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

    String line;
    StringBuilder output = new StringBuilder();
    while ((line = reader.readLine()) != null) {
      output.append(line).append("\n");
    }
    return output.toString();
  }
}
