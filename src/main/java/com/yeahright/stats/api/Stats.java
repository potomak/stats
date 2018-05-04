package com.yeahright.stats.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Stats {
  private double sum;
  private double avg;
  private double min;
  private double max;
  private long count;

  public Stats() {
    this.sum = 0;
    this.avg = 0;
    this.min = Double.POSITIVE_INFINITY;
    this.max = Double.NEGATIVE_INFINITY;
    this.count = 0;
  }

  public Stats(Transaction t) {
    this();
    this.update(t);
  }

  @JsonProperty
  public double getSum() {
    return sum;
  }

  @JsonProperty
  public double getAvg() {
    return avg;
  }

  @JsonProperty
  public double getMin() {
    return min;
  }

  @JsonProperty
  public double getMax() {
    return max;
  }

  @JsonProperty
  public long getCount() {
    return count;
  }

  public void update(Transaction t) {
    this.sum += t.getAmount();
    this.count++;
    this.avg = this.sum / this.count;
    this.min = Math.min(this.min, t.getAmount());
    this.max = Math.max(this.max, t.getAmount());
  }

  public void merge(Stats s) {
    this.sum += s.getSum();
    this.count += s.getCount();
    this.avg = this.sum / this.count;
    this.min = Math.min(this.min, s.getMin());
    this.max = Math.max(this.max, s.getMax());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (obj instanceof Stats) {
      final Stats s = (Stats) obj;
      return getSum() == s.getSum()
          && getAvg() == s.getAvg()
          && getMin() == s.getMin()
          && getMax() == s.getMax()
          && getCount() == s.getCount();
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSum(), getAvg(), getMin(), getMax(), getCount());
  }
}
