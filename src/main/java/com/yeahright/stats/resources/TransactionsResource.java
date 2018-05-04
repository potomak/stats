package com.yeahright.stats.resources;

import com.yeahright.stats.StatsData;
import com.yeahright.stats.api.Transaction;
import com.yeahright.stats.api.Stats;
import com.codahale.metrics.annotation.Timed;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionsResource {
  private StatsData sd;

  public TransactionsResource(StatsData sd) {
    this.sd = sd;
  }

  @GET
  @Timed
  public Stats lastMinuteStats() {
    return this.sd.lastMinuteStats();
  }

  @POST
  @Timed
  public Response storeTransaction(@NotNull Transaction transaction) {
    long now = System.currentTimeMillis();
    long oneMinuteAgo = now - 60 * 1000;
    if (transaction.getTimestamp() > oneMinuteAgo) {
      this.sd.add(transaction);
      return Response.status(Status.CREATED).build();
    } else {
      return Response.status(Status.NO_CONTENT).build();
    }
  }
}
