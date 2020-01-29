package com.roisoftstudio.buildstatus.presentation.api.dto;

import com.roisoftstudio.buildstatus.logic.model.VersionsDiff;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Versions {

  private final String repo;
  private final VersionsDiff dev;
  private final VersionsDiff staging;
  private final VersionsDiff prod;
}
