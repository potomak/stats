package com.yeahright.stats.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {
  private double amount;
  private long timestamp;

  public Transaction() {
    // Jackson deserialization
  }

  public Transaction(double amount, long timestamp) {
    this.amount = amount;
    this.timestamp = timestamp;
  }

  @JsonProperty
  public double getAmount() {
    return amount;
  }

  @JsonProperty
  public long getTimestamp() {
    return timestamp;
  }
}
