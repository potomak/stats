package com.yeahright.stats.resources;

import com.yeahright.stats.StatsData;
import com.yeahright.stats.api.Stats;
import com.yeahright.stats.api.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link TransactionsResource}.
 */
public class TransactionsResourceTest {
  public static final StatsData sd = new StatsData();
  @ClassRule
  public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
      .addResource(new TransactionsResource(sd))
      .build();

  @Test
  public void createTransaction() throws JsonProcessingException {
    final long now = System.currentTimeMillis();
    final Transaction transaction = new Transaction(123.4, now);
    final Response response = RESOURCES.target("/transactions")
        .request(MediaType.APPLICATION_JSON_TYPE)
        .post(Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE));

    assertThat(response.getStatusInfo()).isEqualTo(Response.Status.CREATED);
  }

  @Test
  public void createOldTransaction() throws JsonProcessingException {
    final long now = System.currentTimeMillis();
    final long oneMinuteAgo = now - 60 * 1000;
    final Transaction transaction = new Transaction(123.4, oneMinuteAgo);
    final Response response = RESOURCES.target("/transactions")
        .request(MediaType.APPLICATION_JSON_TYPE)
        .post(Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE));

    assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NO_CONTENT);
  }

  @Test
  public void lastMinuteStats() throws Exception {
    final long now = System.currentTimeMillis();
    final Transaction t = new Transaction(123.4, now);
    final Stats stats = new Stats(t);
    final Stats response = RESOURCES.target("/transactions")
        .request().get(new GenericType<Stats>() {});

    assertThat(response).isEqualTo(stats);
  }
}
