package com.yeahright.stats.api;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Stats}.
 */
public class StatsTest {
  @Test
  public void update() {
    final Stats s = new Stats();
    final long now = System.currentTimeMillis();
    s.update(new Transaction(123.4, now));

    assertThat(s.getSum()).isEqualTo(123.4);
    assertThat(s.getAvg()).isEqualTo(123.4);
    assertThat(s.getMin()).isEqualTo(123.4);
    assertThat(s.getMax()).isEqualTo(123.4);
    assertThat(s.getCount()).isEqualTo(1);

    s.update(new Transaction(100, now));

    assertThat(s.getSum()).isEqualTo(223.4);
    assertThat(s.getAvg()).isEqualTo(111.7);
    assertThat(s.getMin()).isEqualTo(100);
    assertThat(s.getMax()).isEqualTo(123.4);
    assertThat(s.getCount()).isEqualTo(2);
  }

  @Test
  public void merge() {
    final long now = System.currentTimeMillis();
    final Stats s1 = new Stats();
    s1.update(new Transaction(123.4, now));

    assertThat(s1.getSum()).isEqualTo(123.4);
    assertThat(s1.getAvg()).isEqualTo(123.4);
    assertThat(s1.getMin()).isEqualTo(123.4);
    assertThat(s1.getMax()).isEqualTo(123.4);
    assertThat(s1.getCount()).isEqualTo(1);

    final Stats s2 = new Stats();
    s2.update(new Transaction(100, now));

    assertThat(s2.getSum()).isEqualTo(100);
    assertThat(s2.getAvg()).isEqualTo(100);
    assertThat(s2.getMin()).isEqualTo(100);
    assertThat(s2.getMax()).isEqualTo(100);
    assertThat(s2.getCount()).isEqualTo(1);

    s1.merge(s2);

    assertThat(s1.getSum()).isEqualTo(223.4);
    assertThat(s1.getAvg()).isEqualTo(111.7);
    assertThat(s1.getMin()).isEqualTo(100);
    assertThat(s1.getMax()).isEqualTo(123.4);
    assertThat(s1.getCount()).isEqualTo(2);
  }
}
