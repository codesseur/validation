package net.sfr.tv.validation.hard;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.function.Supplier;
import org.immutables.value.Value;

@Value.Immutable
public abstract class TemporalVerifier<T extends Temporal & Comparable<T>> extends
    GenericComparableVerifier<T, TemporalVerifier<T>> {

  abstract Supplier<T> now();

  public OrElse<T> isInTheFuture() {
    return isAfter(now().get());
  }

  public OrElse<T> isInThePast() {
    return isBefore(now().get());
  }

  public OrElse<T> isInTheFuture(TemporalAmount delta) {
    return isAfter((T) now().get().minus(delta));
  }

  public OrElse<T> isAfter(T value) {
    return isGreaterThan(value);
  }

  public OrElse<T> isBefore(T value) {
    return isLessThan(value);
  }

  public OrElse<T> isAtOrAfter(T value) {
    return isGreaterThanOrEqual(value);
  }

  public OrElse<T> isAtOrBefore(T value) {
    return isLessThanOrEqual(value);
  }

  public OrElse<T> isAtOrAfter(T value, TemporalAmount amount) {
    return isBetween(value, (T) value.plus(amount));
  }

  public OrElse<T> isAtOrBefore(T value, TemporalAmount amount) {
    return isBetween((T) value.minus(amount), value);
  }

  @Override
  public TemporalVerifier<T> addContext(String key, Object value) {
    return ImmutableTemporalVerifier.<T>builder().from(this).context(context().add(key, value)).build();
  }

  @Override
  public TemporalVerifier<T> label(String label) {
    return ImmutableTemporalVerifier.<T>builder().from(this).label(label).build();
  }
}
