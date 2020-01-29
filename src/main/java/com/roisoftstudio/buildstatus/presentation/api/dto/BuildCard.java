package com.roisoftstudio.buildstatus.presentation.api.dto;

import java.time.Instant;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BuildCard {
  private final Integer number;
  private final String status;
  private final String event;
  private final String link;
  private final String message;
  private final String before;
  private final String after;
  private final String ref;
  private final String source;
  private final String target;
  private final String author_login;
  private final String author_name;
  private final String author_email;
  private final String author_avatar;
  private final String sender;
  private final Instant started;
  private final Instant duration;

}
