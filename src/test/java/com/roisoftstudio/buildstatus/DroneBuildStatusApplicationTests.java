package com.roisoftstudio.buildstatus;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class DroneBuildStatusApplicationTests {

  @LocalManagementPort
  private int managementPort;

  @Autowired
  private ManagementServerProperties managementServerProperties;

  @Test
  public void healthCheck() {
    assertThat(
        new RestTemplate()
            .getForEntity(
                UriComponentsBuilder.fromHttpUrl(getManagementPath() + "/actuator/health")
                    .build()
                    .toUri(),
                String.class)
            .getStatusCode())
        .isEqualTo(HttpStatus.OK);
  }

  private String getManagementPath() {
    final String managementPath = managementServerProperties.getServlet().getContextPath();

    return "http://localhost:" + managementPort + managementPath;
  }
}
