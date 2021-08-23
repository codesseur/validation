package com.codesseur.validation;

import static java.time.temporal.ChronoUnit.MILLIS;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.function.Supplier;

public abstract class AbstractTemporalVerifier<T extends Temporal & Comparable<T>, S extends AbstractTemporalVerifier<T, S>> extends
    AbstractComparableVerifier<T, S> {

  abstract Supplier<T> now();

  public S isInTheFuture() {
    return isAfter(now().get());
  }

  public S isInThePast() {
    return isBefore(now().get());
  }

  public S isAfterThePast(Duration duration) {
    return isAfter((T) now().get().minus(duration.toMillis(), MILLIS));
  }

  public S isBeforeTheNext(Duration duration) {
    return isBefore((T) now().get().plus(duration.toMillis(), MILLIS));
  }

  public S isAfter(T value) {
    return isGreaterThan(value);
  }

  public S isBefore(T value) {
    return isLessThan(value);
  }

  public S isAtOrAfter(T value) {
    return isGreaterThanOrEqual(value);
  }

  public S isAtOrBefore(T value) {
    return isLessThanOrEqual(value);
  }

}
