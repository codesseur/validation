package net.sfr.tv.validation.condition;

import static net.sfr.tv.validation.condition.ObjectConditions.fromPredicateNonNull;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import net.sfr.tv.validation.Status;

public interface MapConditions {

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> Size(int size, Tuple2<String, Object>... context) {
    return fromPredicateNonNull(actual -> actual.size() == size, context, Tuple.of("_expectedSize", size));
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> NotEmpty(Tuple2<String, Object>... context) {
    return fromPredicateNonNull(actual -> !actual.isEmpty(), context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> Empty(Tuple2<String, Object>... context) {
    return fromPredicateNonNull(Map::isEmpty, context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> NoneKeysMatch(Predicate<K> matcher,
      Tuple2<String, Object>... context) {
    return NoneMatch((k, v) -> matcher.test(k));
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> NoneValuesMatch(Predicate<V> matcher,
      Tuple2<String, Object>... context) {
    return NoneMatch((k, v) -> matcher.test(v), context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> NoneMatch(BiPredicate<K, V> matcher,
      Tuple2<String, Object>... context) {
    return fromPredicateNonNull(c -> c.entrySet().stream().noneMatch(e -> matcher.test(e.getKey(), e.getValue())), context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> AllKeysMatch(Predicate<K> matcher,
      Tuple2<String, Object>... context) {
    return AllMatch((k, v) -> matcher.test(k), context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> allValuesMatch(Predicate<V> matcher,
      Tuple2<String, Object>... context) {
    return AllMatch((k, v) -> matcher.test(v), context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> AllMatch(BiPredicate<K, V> matcher,
      Tuple2<String, Object>... context) {
    return fromPredicateNonNull(c -> c.entrySet().stream().allMatch(e -> matcher.test(e.getKey(), e.getValue())), context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> AnyKeysMatch(Predicate<K> matcher,
      Tuple2<String, Object>... context) {
    return AnyMatch((k, v) -> matcher.test(k), context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> AnyValuesMatch(Predicate<V> matcher,
      Tuple2<String, Object>... context) {
    return AnyMatch((k, v) -> matcher.test(v), context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> AnyMatch(BiPredicate<K, V> matcher,
      Tuple2<String, Object>... context) {
    return fromPredicateNonNull(c -> c.entrySet().stream().anyMatch(e -> matcher.test(e.getKey(), e.getValue())), context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> ContainsKey(K key, Tuple2<String, Object>... context) {
    return fromPredicateNonNull(c -> c.containsKey(key), context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> NotContainsKey(K key, Tuple2<String, Object>... context) {
    return fromPredicateNonNull(c -> !c.containsKey(key), context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> ContainsAllKeys(Collection<K> keys,
      Tuple2<String, Object>... context) {
    Set<K> set = new HashSet<>(keys);
    return AllKeysMatch(set::contains, context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> ContainsAnyKeys(Collection<K> keys,
      Tuple2<String, Object>... context) {
    Set<K> set = new HashSet<>(keys);
    return AnyKeysMatch(set::contains, context);
  }

  @SafeVarargs
  static <K, V, T extends Map<K, V>> Function<T, Status> NotContainsAnyKeys(Collection<K> keys,
      Tuple2<String, Object>... context) {
    Set<K> set = new HashSet<>(keys);
    return NoneKeysMatch(set::contains, context);
  }

}
