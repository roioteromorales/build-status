package com.roisoftstudio.buildstatus.logic.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VersionsDiff {

  private final String status;
  private final String statusMessage;
  private final String commitMessage;
  private final String commitLink;
  private final String commitHash;
  private final String author;
  private final String droneBuildLink;
  private final String droneBuildStatus;
  private final Integer droneBuildNumber;
  private final LocalDateTime datetime;
}
