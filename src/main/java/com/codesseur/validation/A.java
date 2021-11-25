package com.codesseur.validation;

import com.codesseur.iterate.container.Bag;
import com.codesseur.iterate.container.Dictionary;
import com.codesseur.iterate.container.Sequence;
import java.time.Instant;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class A {

  public static <E, C extends Bag<E>> BagVerifier<E, C> bag(Conditions<C> conditions) {
    return ImmutableBagVerifier.<E, C>builder().conditions(conditions).build();
  }

  public static BooleanVerifier bool(Conditions<Boolean> conditions) {
    return ImmutableBooleanVerifier.builder().conditions(conditions).build();
  }

  public static <E, T extends Collection<E>> CollectionVerifier<E, T> collection(Conditions<T> conditions) {
    return ImmutableCollectionVerifier.<E, T>builder().conditions(conditions).build();
  }

  public static <V extends Comparable<V>> ComparableVerifier<V> comparable(Conditions<V> conditions) {
    return ImmutableComparableVerifier.<V>builder().conditions(conditions).build();
  }

  public static <K, V, T extends Dictionary<K, V>> DictionaryVerifier<K, V, T> dictionary(Conditions<T> conditions) {
    return ImmutableDictionaryVerifier.<K, V, T>builder().conditions(conditions).build();
  }

  public static InstantVerifier instant(Conditions<Instant> conditions) {
    return ImmutableInstantVerifier.builder().conditions(conditions).build();
  }

  public static LocalDateTimeVerifier localDateTime(Conditions<ChronoLocalDateTime<?>> conditions) {
    return ImmutableLocalDateTimeVerifier.builder().conditions(conditions).build();
  }

  public static <K, V, T extends Map<K, V>> MapVerifier<K, V, T> map(Conditions<T> conditions) {
    return ImmutableMapVerifier.<K, V, T>builder().conditions(conditions).build();
  }

  public static <T> ObjectVerifier<T> object(Conditions<T> conditions) {
    return ImmutableObjectVerifier.<T>builder().conditions(conditions).build();
  }

  public static <T> OptionalVerifier<T> optional(Conditions<Optional<T>> conditions) {
    return ImmutableOptionalVerifier.<T>builder().conditions(conditions).build();
  }

  public static <E, C extends Sequence<E>> SequenceVerifier<E, C> sequence(Conditions<C> conditions) {
    return ImmutableSequenceVerifier.<E, C>builder().conditions(conditions).build();
  }

  public static StringVerifier string(Conditions<String> conditions) {
    return ImmutableStringVerifier.builder().conditions(conditions).build();
  }
}
