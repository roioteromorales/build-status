package com.roisoftstudio.buildstatus.logic;

import static org.assertj.core.api.Assertions.assertThat;

import com.roisoftstudio.buildstatus.logic.helpers.ShellExecutor;
import com.roisoftstudio.buildstatus.logic.helpers.ShellResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DroneCliCommanderTest {


  private DroneCliCommander droneCliCommander = new DroneCliCommander(new ShellExecutor());

  @Test
  public void shouldExecute() {

//    ShellResponse shellResponse = droneCliCommander.promoteBuild("red-dummy", 133, "staging");
//
//    assertThat(shellResponse.getStatus()).isEqualTo(0);
//    assertThat(shellResponse.getOutput()).isEqualTo("Number: 137\n"
//        + "Status: pending\n"
//        + "Event: promote\n"
//        + "Commit: cf18d918fda09378dcff6aebeacc5ee8f38380a3\n"
//        + "Branch: master\n"
//        + "Ref: refs/heads/master\n"
//        + "Author:  <roi.otero.morales@infinityworks.com>\n"
//        + "Message: Change accepted type for auth\n");

  }
}