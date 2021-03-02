package net.sfr.tv.validation.condition;

import static net.sfr.tv.validation.condition.ObjectConditions.fromPredicate;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.function.Function;
import java.util.function.IntPredicate;
import net.sfr.tv.validation.Status;

public interface ComparableConditions {
  @SafeVarargs
  static <T extends Comparable<T>> Function<T, Status> Between(T start, T end, Tuple2<String, Object>... context) {
    return fromPredicate(e -> e.compareTo(start) >= 0 && e.compareTo(end) <= 0, context, Tuple.of("_min", start), Tuple.of("_max", end));
  }
  @SafeVarargs
  static <T extends Comparable<T>> Function<T, Status> GreaterThan(T value, Tuple2<String, Object>... context) {
    return compare(value, v -> v > 0, "_min", context );
  }
  @SafeVarargs
  static <T extends Comparable<T>> Function<T, Status> LessThan(T value, Tuple2<String, Object>... context) {
    return compare(value, v -> v < 0, "_max",context);
  }
  @SafeVarargs
  static <T extends Comparable<T>> Function<T, Status> GreaterThanOrEqual(T value, Tuple2<String, Object>... context) {
    return compare(value, v -> v >= 0, "_min", context);
  }
  @SafeVarargs
  static <T extends Comparable<T>> Function<T, Status> LessThanOrEqual(T value, Tuple2<String, Object>... context) {
    return compare(value, v -> v <= 0, "_max", context);
  }

  @SafeVarargs
  private static <T extends Comparable<T>> Function<T, Status> compare(T value, IntPredicate condition, String label, Tuple2<String, Object>... context) {
    return fromPredicate(actual -> condition.test(actual.compareTo(value)), context, Tuple.of(label, value));
  }

}
