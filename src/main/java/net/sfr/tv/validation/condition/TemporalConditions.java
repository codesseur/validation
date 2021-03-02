package net.sfr.tv.validation.condition;

import io.vavr.Tuple2;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.function.Function;
import java.util.function.Supplier;
import net.sfr.tv.validation.Status;

public interface TemporalConditions {

  @SafeVarargs
  static <T extends Temporal & Comparable<T>> Function<T, Status> InTheFuture(Supplier<T> now,
      Tuple2<String, Object>... context) {
    return After(now.get(), context);
  }

  @SafeVarargs
  static <T extends Temporal & Comparable<T>> Function<T, Status> InThePast(Supplier<T> now,
      Tuple2<String, Object>... context) {
    return Before(now.get(), context);
  }

  @SafeVarargs
  static <T extends Temporal & Comparable<T>> Function<T, Status> InTheFuture(TemporalAmount delta, Supplier<T> now,
      Tuple2<String, Object>... context) {
    return After((T) now.get().minus(delta), context);
  }

  @SafeVarargs
  static <T extends Temporal & Comparable<T>> Function<T, Status> After(T value, Tuple2<String, Object>... context) {
    return ComparableConditions.GreaterThan(value, context);
  }

  @SafeVarargs
  static <T extends Temporal & Comparable<T>> Function<T, Status> Before(T value, Tuple2<String, Object>... context) {
    return ComparableConditions.LessThan(value, context);
  }

  @SafeVarargs
  static <T extends Temporal & Comparable<T>> Function<T, Status> AtOrAfter(T value,
      Tuple2<String, Object>... context) {
    return ComparableConditions.GreaterThanOrEqual(value, context);
  }

  @SafeVarargs
  static <T extends Temporal & Comparable<T>> Function<T, Status> AtOrBefore(T value,
      Tuple2<String, Object>... context) {
    return ComparableConditions.LessThanOrEqual(value, context);
  }

  @SafeVarargs
  static <T extends Temporal & Comparable<T>> Function<T, Status> InWindow(T value, TemporalAmount amount,
      Tuple2<String, Object>... context) {
    return ComparableConditions.Between(value, (T) value.plus(amount), context);
  }
}
