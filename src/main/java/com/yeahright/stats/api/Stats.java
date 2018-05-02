package com.yeahright.stats.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stats {
  private long count;

  public Stats() {
    // Jackson deserialization
  }

  public Stats(long count) {
    this.count = count;
  }

  @JsonProperty
  public long getCount() {
    return count;
  }
}
