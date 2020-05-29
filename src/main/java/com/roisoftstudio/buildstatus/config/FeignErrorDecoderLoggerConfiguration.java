package com.roisoftstudio.buildstatus.config;

import static feign.FeignException.errorStatus;

import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FeignErrorDecoderLoggerConfiguration {

  @Bean
  public ErrorDecoder errorDecoder() {
    return (methodKey, response) -> {
      try {
        log.info("{} - {} - {}", response.request().url(), response.status(), IOUtils.toString(response.body().asReader()));
      } catch (Exception ignored) {
      }
      return errorStatus(methodKey, response);
    };
  }
}
