package com.yeahright.stats;

import com.yeahright.stats.api.Stats;
import com.yeahright.stats.api.Transaction;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link StatsData}.
 */
public class StatsDataTest {
  @Test
  public void addMergesStatsForTransactionThatHappenInTheSameSecond() {
    final StatsData sd = new StatsData();
    final long now = System.currentTimeMillis();
    sd.add(new Transaction(123.4, now));
    sd.add(new Transaction(123.4, now));

    assertThat(sd.size()).isEqualTo(1);
    assertThat(sd.lastMinuteStats().getCount()).isEqualTo(2);
  }

  @Test
  public void addCreatesMultipleStatsForTransactionsThatHappenSecondsApart() {
    final StatsData sd = new StatsData();
    final long now = System.currentTimeMillis();
    sd.add(new Transaction(123.4, now));
    sd.add(new Transaction(123.4, now - 1000));

    assertThat(sd.size()).isEqualTo(2);
    assertThat(sd.lastMinuteStats().getCount()).isEqualTo(2);
  }

  @Test
  public void addTransactionInTheMiddle() {
    final StatsData sd = new StatsData();
    final long now = System.currentTimeMillis();
    sd.add(new Transaction(123.4, now));
    sd.add(new Transaction(123.4, now - 2000));
    sd.add(new Transaction(123.4, now - 1000));

    assertThat(sd.size()).isEqualTo(3);
    assertThat(sd.lastMinuteStats().getCount()).isEqualTo(3);
  }

  @Test
  public void addRemovesStatsOlderThanOneMinute() throws InterruptedException {
    final StatsData sd = new StatsData();
    final long t1 = System.currentTimeMillis();
    sd.add(new Transaction(123.4, t1 - 60 * 1000));

    assertThat(sd.size()).isEqualTo(1);

    Thread.sleep(1000);
    final long t2 = System.currentTimeMillis();
    sd.add(new Transaction(123.4, t2));

    assertThat(sd.size()).isEqualTo(1);
    assertThat(sd.lastMinuteStats().getCount()).isEqualTo(1);
  }
}
