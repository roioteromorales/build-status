package com.roisoftstudio.buildstatus.config;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfiguration {

  private static final String DATABASE_NAME = "database.db";

  @Bean
  public DB initializeDb() {
    return DBMaker.fileDB(DATABASE_NAME).make();
  }
}
