package com.codesseur.validation;

import com.codesseur.iterate.Streamed;
import com.codesseur.iterate.container.Dictionary;
import io.vavr.Lazy;
import io.vavr.Tuple;
import java.util.Arrays;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.immutables.value.Value;

@Value.Immutable
public abstract class DictionaryVerifier<K, V, T extends Dictionary<K, V>> extends
    Verifier<T, DictionaryVerifier<K, V, T>> {

  public static <K, V, T extends Dictionary<K, V>> DictionaryVerifier<K, V, T> me(Lazy<? extends T> value) {
    return ImmutableDictionaryVerifier.<K, V, T>builder().value(value).build();
  }

  public DictionaryVerifier<K, V, T> hasSize(int size) {
    return isNotNull().then()
        .satisfies(e -> e.size() == size, () -> Failure.of("SIZE_MISMATCH", Tuple.of("size", size)));
  }

  public DictionaryVerifier<K, V, T> isNotEmpty() {
    return isNotNull().then().satisfies(es -> !es.isEmpty(), () -> Failure.of("EMPTY"));
  }

  public DictionaryVerifier<K, V, T> isEmpty() {
    return isNotNull().then().satisfies(Dictionary::isEmpty, () -> Failure.of("NOT_EMPTY"));
  }

  public DictionaryVerifier<K, V, T> noneKeysMatch(Predicate<? super K> matcher) {
    return noneMatch((k, v) -> matcher.test(k));
  }

  public DictionaryVerifier<K, V, T> noneValuesMatch(Predicate<? super V> matcher) {
    return noneMatch((k, v) -> matcher.test(v));
  }

  public DictionaryVerifier<K, V, T> noneMatch(BiPredicate<? super K, ? super V> matcher) {
    return satisfies(c -> c.stream().noneMatch(e -> matcher.test(e.getKey(), e.getValue())),
        () -> Failure.of("MATCH"));
  }

  public DictionaryVerifier<K, V, T> allKeysMatch(Predicate<? super K> matcher) {
    return allMatch((k, v) -> matcher.test(k));
  }

  public DictionaryVerifier<K, V, T> allValuesMatch(Predicate<? super V> matcher) {
    return allMatch((k, v) -> matcher.test(v));
  }

  public DictionaryVerifier<K, V, T> allMatch(BiPredicate<? super K, ? super V> matcher) {
    return satisfies(c -> c.stream().allMatch(e -> matcher.test(e.getKey(), e.getValue())),
        () -> Failure.of("NOT_MATCH"));
  }

  public DictionaryVerifier<K, V, T> anyKeyMatches(Predicate<? super K> matcher) {
    return anyMatches((k, v) -> matcher.test(k));
  }

  public DictionaryVerifier<K, V, T> anyValueMatches(Predicate<? super V> matcher) {
    return anyMatches((k, v) -> matcher.test(v));
  }

  public DictionaryVerifier<K, V, T> anyMatches(BiPredicate<? super K, ? super V> matcher) {
    return satisfies(c -> c.stream().anyMatch(e -> matcher.test(e.getKey(), e.getValue())),
        () -> Failure.of("NOT_MATCH"));
  }

  public DictionaryVerifier<K, V, T> containsKey(K key) {
    return satisfies(c -> c.hasKey(key), () -> Failure.of("NOT_CONTAIN", Tuple.of("key", key)));
  }

  public DictionaryVerifier<K, V, T> notContainsKey(K key) {
    return satisfies(c -> !c.hasKey(key), () -> Failure.of("CONTAINS", Tuple.of("key", key)));
  }

  public DictionaryVerifier<K, V, T> containsAllKeys(Iterable<? extends K> keys) {
    Set<K> set = Streamed.<K>of(keys).toSet();
    return allKeysMatch(set::contains);
  }

  public DictionaryVerifier<K, V, T> containsAnyKeys(Iterable<? extends K> keys) {
    Set<K> set = Streamed.<K>of(keys).toSet();
    return anyKeyMatches(set::contains);
  }

  public DictionaryVerifier<K, V, T> notContainsAnyKeys(Iterable<? extends K> keys) {
    Set<K> set = Streamed.<K>of(keys).toSet();
    return noneKeysMatch(set::contains);
  }

  @SafeVarargs
  public final DictionaryVerifier<K, V, T> containsAllKeys(K... keys) {
    return containsAllKeys(Arrays.asList(keys));
  }

  @SafeVarargs
  public final DictionaryVerifier<K, V, T> containsAnyKeys(K... keys) {
    return containsAnyKeys(Arrays.asList(keys));
  }

  @SafeVarargs
  public final DictionaryVerifier<K, V, T> notContainsAnyKeys(K... keys) {
    return notContainsAnyKeys(Arrays.asList(keys));
  }

}
