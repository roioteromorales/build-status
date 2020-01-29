package com.roisoftstudio.buildstatus.logic.helpers;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ShellResponse {

  private final String output;
  private final Integer status;
}
