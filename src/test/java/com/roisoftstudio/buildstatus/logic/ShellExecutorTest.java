package com.roisoftstudio.buildstatus.logic;

import static org.assertj.core.api.Assertions.assertThat;

import com.roisoftstudio.buildstatus.logic.helpers.ShellExecutor;
import com.roisoftstudio.buildstatus.logic.helpers.ShellResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShellExecutorTest {

  @InjectMocks
  private ShellExecutor shellExecutor;

  @Test
  public void testExecuteCommand() {
    ShellResponse commandResult = shellExecutor.executeCommand("pwd");

    assertThat(commandResult.getOutput()).isEqualTo("/Users/roi/IdeaProjects/build-status\n");
    assertThat(commandResult.getStatus()).isEqualTo(0);
  }
}