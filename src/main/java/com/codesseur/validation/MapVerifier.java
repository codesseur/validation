package com.codesseur.validation;

import com.codesseur.iterate.Streamed;
import io.vavr.Tuple;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.immutables.value.Value;

@Value.Immutable
public abstract class MapVerifier<K, V, T extends Map<K, V>> extends Verifier<T, MapVerifier<K, V, T>> {

  public MapVerifier<K, V, T> hasSize(int size) {
    return isNotNull().then()
        .satisfies(e -> e.size() == size, () -> Failures.of("SIZE_MISMATCH", Tuple.of("size", size)));
  }

  public MapVerifier<K, V, T> isNotEmpty() {
    return isNotNull().then().satisfies(es -> !es.isEmpty(), () -> Failures.of("EMPTY"));
  }

  public MapVerifier<K, V, T> isEmpty() {
    return isNotNull().then().satisfies(Map::isEmpty, () -> Failures.of("NOT_EMPTY"));
  }

  public MapVerifier<K, V, T> noneKeysMatch(Predicate<? super K> matcher) {
    return noneMatch((k, v) -> matcher.test(k));
  }

  public MapVerifier<K, V, T> noneValuesMatch(Predicate<? super V> matcher) {
    return noneMatch((k, v) -> matcher.test(v));
  }

  public MapVerifier<K, V, T> noneMatch(BiPredicate<? super K, ? super V> matcher) {
    return satisfies(c -> c.entrySet().stream().noneMatch(e -> matcher.test(e.getKey(), e.getValue())),
        () -> Failures.of("MATCH"));
  }

  public MapVerifier<K, V, T> allKeysMatch(Predicate<? super K> matcher) {
    return allMatch((k, v) -> matcher.test(k));
  }

  public MapVerifier<K, V, T> allValuesMatch(Predicate<? super V> matcher) {
    return allMatch((k, v) -> matcher.test(v));
  }

  public MapVerifier<K, V, T> allMatch(BiPredicate<? super K, ? super V> matcher) {
    return satisfies(c -> c.entrySet().stream().allMatch(e -> matcher.test(e.getKey(), e.getValue())),
        () -> Failures.of("NOT_MATCH"));
  }

  public MapVerifier<K, V, T> anyKeyMatches(Predicate<? super K> matcher) {
    return anyMatches((k, v) -> matcher.test(k));
  }

  public MapVerifier<K, V, T> anyValueMatches(Predicate<? super V> matcher) {
    return anyMatches((k, v) -> matcher.test(v));
  }

  public MapVerifier<K, V, T> anyMatches(BiPredicate<? super K, ? super V> matcher) {
    return satisfies(c -> c.entrySet().stream().anyMatch(e -> matcher.test(e.getKey(), e.getValue())),
        () -> Failures.of("NOT_MATCH"));
  }

  public MapVerifier<K, V, T> containsKey(K key) {
    return satisfies(c -> c.containsKey(key), () -> Failures.of("NOT_CONTAIN", Tuple.of("key", key)));
  }

  public MapVerifier<K, V, T> notContainsKey(K key) {
    return satisfies(c -> !c.containsKey(key), () -> Failures.of("CONTAINS", Tuple.of("key", key)));
  }

  @SafeVarargs
  public final MapVerifier<K, V, T> containsAllKeys(K... keys) {
    return containsAllKeys(Arrays.asList(keys));
  }

  public MapVerifier<K, V, T> containsAllKeys(Iterable<? extends K> keys) {
    Set<K> set = Streamed.<K>of(keys).toSet();
    return allKeysMatch(set::contains);
  }

  @SafeVarargs
  public final MapVerifier<K, V, T> containsAnyKeys(K... keys) {
    return containsAnyKeys(Arrays.asList(keys));
  }

  public MapVerifier<K, V, T> containsAnyKeys(Iterable<? extends K> keys) {
    Set<K> set = Streamed.<K>of(keys).toSet();
    return anyKeyMatches(set::contains);
  }

  @SafeVarargs
  public final MapVerifier<K, V, T> notContainsAnyKeys(K... keys) {
    return notContainsAnyKeys(Arrays.asList(keys));
  }

  public MapVerifier<K, V, T> notContainsAnyKeys(Iterable<? extends K> keys) {
    Set<K> set = Streamed.<K>of(keys).toSet();
    return noneKeysMatch(set::contains);
  }

}
