package net.sfr.tv.validation;

import static net.sfr.tv.validation.ToBe.invalid;
import static net.sfr.tv.validation.condition.TemporalConditions.After;
import static net.sfr.tv.validation.condition.TemporalConditions.AtOrAfter;
import static net.sfr.tv.validation.condition.TemporalConditions.AtOrBefore;
import static net.sfr.tv.validation.condition.TemporalConditions.Before;
import static net.sfr.tv.validation.condition.TemporalConditions.InTheFuture;
import static net.sfr.tv.validation.condition.TemporalConditions.InThePast;

import java.time.Instant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyInstantTest {

  private Instant now = Instant.now();
  private Instant past = now.minusSeconds(60);
  private Instant future = now.plusSeconds(60);

  @Test
  public void futureIsInTheFuture() {
    Validate.that(future).is(InTheFuture(Instant::now), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void pastIsInThePast() {
    Validate.that(past).is(InThePast(Instant::now), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void nowIsBeforeFuture() {
    Validate.that(now).is(Before(future), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void nowIsAfterPast() {
    Validate.that(now).is(After(past), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void nowIsAtOrBeforeFuture() {
    Validate.that(now).is(AtOrBefore(future), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void nowIsAtOrAfterPast() {
    Validate.that(now).is(AtOrAfter(past), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void nowIsAtOrBeforenow() {
    Validate.that(now).is(AtOrBefore(now), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void nowIsAtOrAfternow() {
    Validate.that(now).is(AtOrAfter(now), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void nowIsNotBeforePast() {
    Assertions.assertThatThrownBy(() -> Validate.that(now).is(Before(past), invalid("INVALID"))
        .orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void nowIsNotAfterFuture() {
    Assertions.assertThatThrownBy(() -> Validate.that(now).is(After(future), invalid("INVALID"))
        .orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

}