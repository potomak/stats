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

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (obj instanceof Stats) {
      final Stats s = (Stats) obj;
      return getCount() == s.getCount();
    }

    return false;
  }
}
