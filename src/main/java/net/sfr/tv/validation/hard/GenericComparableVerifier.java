package net.sfr.tv.validation.hard;

import java.util.function.IntPredicate;

public abstract class GenericComparableVerifier<T extends Comparable<T>, S extends GenericComparableVerifier<T,S>> extends
    GenericVerifier<T, S> {

  public OrElse<T> isBetween(T start, T end) {
    return addContext("start", start)
        .addContext("end", end)
        .satisfies(e -> e.compareTo(start) >= 0 && e.compareTo(end) <= 0);
  }

  public OrElse<T> isGreaterThan(T value) {
    return compare(value, v -> v > 0);
  }

  public OrElse<T> isLessThan(T value) {
    return compare(value, v -> v < 0);
  }

  public OrElse<T> isGreaterThanOrEqual(T value) {
    return compare(value, v -> v >= 0);
  }

  public OrElse<T> isLessThanOrEqual(T value) {
    return compare(value, v -> v <= 0);
  }

  public OrElse<T> compare(T value, IntPredicate condition) {
    return addContext("other", value).satisfies(e -> condition.test(e.compareTo(value)));
  }

}
