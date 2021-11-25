package com.codesseur.validation;

import io.vavr.Tuple;
import java.util.function.IntPredicate;

public abstract class AbstractComparableVerifier<V extends Comparable<V>, S extends AbstractComparableVerifier<V, S>> extends
    Verifier<V, S> {

  public S isBetween(V start, V end) {
    return isNotNull().then().satisfies(e -> e.compareTo(start) >= 0 && e.compareTo(end) <= 0,
        () -> Failures.of("NOT_BETWEEN", Tuple.of("start", start), Tuple.of("end", end)));
  }

  public S isGreaterThan(V value) {
    return compare(value, v -> v > 0);
  }

  public S isLessThan(V value) {
    return compare(value, v -> v < 0);
  }

  public S isGreaterThanOrEqual(V value) {
    return compare(value, v -> v >= 0);
  }

  public S isLessThanOrEqual(V value) {
    return compare(value, v -> v <= 0);
  }

  public S compare(V value, IntPredicate condition) {
    return isNotNull().then().satisfies(e -> condition.test(e.compareTo(value)), () ->
        Failures.of("NOT_NULL", Tuple.of("other", value)));
  }
}
