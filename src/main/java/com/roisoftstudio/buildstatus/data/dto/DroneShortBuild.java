package com.roisoftstudio.buildstatus.data.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DroneShortBuild {
  private final Integer number;
  private final String display;
  private String link;

  public static class DroneShortBuildBuilder {

    public DroneShortBuild.DroneShortBuildBuilder link(String url, String organization, String repo) {
      this.link = url.concat("/").concat(organization).concat("/").concat(repo).concat("/").concat(number.toString());
      return this;
    }

    public DroneShortBuild.DroneShortBuildBuilder display(String author, String message) {
      this.display = number.toString().concat(" - ").concat(message).concat("...[").concat(author).concat("]");
      return this;
    }
  }
}
