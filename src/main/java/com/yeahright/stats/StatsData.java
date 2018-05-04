package com.yeahright.stats;

import com.yeahright.stats.api.Stats;
import com.yeahright.stats.api.Transaction;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StatsData {
  private List<StatsItem> data;
  private final Object lock;

  public StatsData() {
    this.data = new LinkedList<StatsItem>();
    this.lock = new Object();
  }

  public int size() {
    synchronized (this.lock) {
      return this.data.size();
    }
  }

  public void add(Transaction t) {
    final long tSec = t.getTimestamp() / 1000;
    final List<StatsItem> newData = new LinkedList<StatsItem>();

    synchronized (this.lock) {
      final long sec = System.currentTimeMillis() / 1000;
      final long oneMinuteAgo = sec - 60;
      final Iterator it = this.data.iterator();
      boolean added = false;
      while (it.hasNext()) {
        final StatsItem c = (StatsItem) it.next();
        if (!added) {
          if (c.getTimestamp() == tSec) {
            c.update(t);
            added = true;
          }
          if (c.getTimestamp() < tSec) {
            newData.add(new StatsItem(tSec, new Stats(t)));
            added = true;
          }
        }

        if (c.getTimestamp() < oneMinuteAgo) {
          break;
        }

        newData.add(c);
      }

      if (!added) {
        newData.add(new StatsItem(tSec, new Stats(t)));
      }

      this.data = newData;
    }
  }

  public Stats lastMinuteStats() {
    final long sec = System.currentTimeMillis() / 1000;
    final long oneMinuteAgo = sec - 60;
    final Stats res = new Stats();

    synchronized (this.lock) {
      final Iterator it = this.data.iterator();
      while (it.hasNext()) {
        final StatsItem c = (StatsItem) it.next();
        if (c.getTimestamp() >= oneMinuteAgo) {
          res.merge(c.getStats());
        }
      }
    }

    return res;
  }

  private static class StatsItem {
    private long timestamp;
    private Stats stats;

    public StatsItem(long timestamp, Stats stats) {
      this.timestamp = timestamp;
      this.stats = stats;
    }

    public long getTimestamp() {
      return this.timestamp;
    }

    public Stats getStats() {
      return this.stats;
    }

    public void update(Transaction t) {
      this.stats.update(t);
    }
  }
}
