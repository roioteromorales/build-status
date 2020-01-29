package com.roisoftstudio.buildstatus.data.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GithubCompare {

  private final String status;
  private final Integer ahead_by;
  private final Integer behind_by;
  private final BaseCommit base_commit;
  private final BaseCommit merge_base_commit;

  @Value
  @Builder
  public static class BaseCommit {

    private final String sha;
    private final String html_url;
    private final Commit commit;

    @Value
    @Builder
    public static class Commit {

      private final String message;
      private final String comment_count;
      private final Author author;

      @Value
      @Builder
      public static class Author {

        private final String name;
        private final LocalDateTime date;
      }
    }
  }
}
