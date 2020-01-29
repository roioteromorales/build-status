package com.roisoftstudio.buildstatus.data.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DroneRepo {

  private final String name;
  private final String link;
}
