package com.roisoftstudio.buildstatus.config;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("test")
public class DbConfiguration {

  @Bean
  public DB initializeDb() {
    return DBMaker.fileDB("test.db").make();
  }
}
