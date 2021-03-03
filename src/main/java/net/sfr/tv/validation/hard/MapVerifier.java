package net.sfr.tv.validation.hard;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.immutables.value.Value;

@Value.Immutable
public abstract class MapVerifier<K, V, T extends Map<K, V>> extends
    GenericVerifier<T, MapVerifier<K, V, T>> {

  @Override
  public MapVerifier<K, V, T> addContext(String key, Object value) {
    return ImmutableMapVerifier.<K, V, T>builder().from(this).context(context().add(key, value))
        .build();
  }

  @Override
  public MapVerifier<K, V, T> label(String label) {
    return ImmutableMapVerifier.<K, V, T>builder().from(this).label(label).build();
  }

  public OrElse<T> hasSize(int size) {
    return addContext("size", size)
        .satisfiesWithOptional(op -> op.filter(e -> e.size() == size).isPresent());
  }

  public OrElse<T> isNotEmpty() {
    return satisfies(es -> !es.isEmpty());
  }

  public OrElse<T> isEmpty() {
    return satisfies(Map::isEmpty);
  }

  public OrElse<T> noneKeysMatch(Predicate<K> matcher) {
    return noneMatch((k, v) -> matcher.test(k));
  }

  public OrElse<T> noneValuesMatch(Predicate<V> matcher) {
    return noneMatch((k, v) -> matcher.test(v));
  }

  public OrElse<T> noneMatch(BiPredicate<K, V> matcher) {
    return satisfies(
        c -> c.entrySet().stream().noneMatch(e -> matcher.test(e.getKey(), e.getValue())));
  }

  public OrElse<T> allKeysMatch(Predicate<K> matcher) {
    return allMatch((k, v) -> matcher.test(k));
  }

  public OrElse<T> allValuesMatch(Predicate<V> matcher) {
    return allMatch((k, v) -> matcher.test(v));
  }

  public OrElse<T> allMatch(BiPredicate<K, V> matcher) {
    return satisfies(c -> c.entrySet().stream().allMatch(e -> matcher.test(e.getKey(), e.getValue())));
  }

  public OrElse<T> anyKeysMatch(Predicate<K> matcher) {
    return anyMatch((k, v) -> matcher.test(k));
  }

  public OrElse<T> anyValuesMatch(Predicate<V> matcher) {
    return anyMatch((k, v) -> matcher.test(v));
  }

  public OrElse<T> anyMatch(BiPredicate<K, V> matcher) {
    return satisfies(c -> c.entrySet().stream().anyMatch(e -> matcher.test(e.getKey(), e.getValue())));
  }

  public OrElse<T> containsKey(K key) {
    return satisfies(c -> c.containsKey(key));
  }

  public OrElse<T> notContainsKey(K key) {
    return satisfies(c -> !c.containsKey(key));
  }

  public OrElse<T> containsAllKeys(Collection<K> keys) {
    Set<K> set = new HashSet<>(keys);
    return allKeysMatch(set::contains);
  }

  public OrElse<T> containsAnyKeys(Collection<K> keys) {
    Set<K> set = new HashSet<>(keys);
    return anyKeysMatch(set::contains);
  }

  public OrElse<T> notContainsAnyKeys(Collection<K> keys) {
    Set<K> set = new HashSet<>(keys);
    return noneKeysMatch(set::contains);
  }

}
