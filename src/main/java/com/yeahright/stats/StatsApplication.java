package com.yeahright.stats;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.yeahright.stats.resources.TransactionsResource;

public class StatsApplication extends Application<StatsConfiguration> {
  private StatsData sd;

  public static void main(String[] args) throws Exception {
    new StatsApplication().run(args);
  }

  @Override
  public void initialize(Bootstrap<StatsConfiguration> bootstrap) {
    this.sd = new StatsData();
  }

  @Override
  public void run(StatsConfiguration configuration,
                  Environment environment) {
    final TransactionsResource resource = new TransactionsResource(this.sd);
    environment.jersey().register(resource);
  }
}
