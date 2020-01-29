package com.roisoftstudio.buildstatus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BuildStatusApplication {

  public static void main(String[] args) {
    SpringApplication.run(BuildStatusApplication.class, args);
  }

}
