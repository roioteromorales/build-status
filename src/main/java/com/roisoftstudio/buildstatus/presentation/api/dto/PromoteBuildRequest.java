package com.roisoftstudio.buildstatus.presentation.api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PromoteBuildRequest {

  private final String repo;
  private final Integer buildNumber;
  private final String environment;
}
