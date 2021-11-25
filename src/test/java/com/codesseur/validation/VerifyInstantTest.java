package com.codesseur.validation;

import static java.time.Duration.ofHours;
import static java.time.Duration.ofSeconds;

import java.time.Instant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyInstantTest {

  private final Instant now = Instant.now();
  private final Instant past = now.minusSeconds(60);
  private final Instant future = now.plusSeconds(60);

  @Test
  public void futureIsInTheFuture() {
    Verify.that(future).isInTheFuture().otherwiseThrow();
  }

  @Test
  public void pastIsInThePast() {
    Verify.that(past).isInThePast().otherwiseThrow();
  }

  @Test
  public void nowIsBeforeFuture() {
    Verify.that(now).isBefore(future).otherwiseThrow();
  }

  @Test
  public void nowIsAfterPast() {
    Verify.that(now).isAfter(past).otherwiseThrow();
  }

  @Test
  public void nowIsAtOrBeforeFuture() {
    Verify.that(now).isAtOrBefore(future).otherwiseThrow();
  }

  @Test
  public void nowIsAtOrAfterPast() {
    Verify.that(now).isAtOrAfter(past).otherwiseThrow();
  }

  @Test
  public void nowIsAtOrBeforeNow() {
    Verify.that(now).isAtOrBefore(now).otherwiseThrow();
  }

  @Test
  public void nowIsAtOrAfterNow() {
    Verify.that(now).isAtOrAfter(now).otherwiseThrow();
  }

  @Test
  public void nowIsNotBeforePast() {
    Assertions.assertThatThrownBy(() -> Verify.that(now).isBefore(past).otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void nowIsNotAfterFuture() {
    Assertions.assertThatThrownBy(() -> Verify.that(now).isAfter(future).otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void isAfterThePastOk() {
    Verify.that(past).isAfterThePast(ofHours(1)).otherwiseThrow();
  }

  @Test
  public void isAfterThePastKo() {
    Assertions.assertThatThrownBy(() -> Verify.that(past).isAfterThePast(ofSeconds(1)).otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void isBeforeTheNextOk() {
    Verify.that(future).isBeforeTheNext(ofHours(1)).otherwiseThrow();
  }

  @Test
  public void isBeforeTheNextKo() {
    Assertions.assertThatThrownBy(() -> Verify.that(future).isBeforeTheNext(ofSeconds(1)).otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

}