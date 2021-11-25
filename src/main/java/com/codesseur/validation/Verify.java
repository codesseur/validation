package com.codesseur.validation;

import static com.codesseur.validation.Conditions.success;

import com.codesseur.iterate.container.Bag;
import com.codesseur.iterate.container.Dictionary;
import com.codesseur.iterate.container.Sequence;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class Verify {

  public static StringVerifier that(String value) {
    return A.string(success(value));
  }

  public static <E, T extends Collection<E>> CollectionVerifier<E, T> that(T value) {
    return A.collection(success(value));
  }

  public static <E, T extends Sequence<E>> SequenceVerifier<E, T> that(T value) {
    return A.sequence(success(value));
  }

  public static <E, T extends Bag<E>> BagVerifier<E, T> that(T value) {
    return A.bag(success(value));
  }

  public static <T> ObjectVerifier<T> that(T value) {
    return A.object(success(value));
  }

  public static <T> OptionalVerifier<T> that(Optional<T> value) {
    return A.optional(success(value));
  }

  public static LocalDateTimeVerifier that(LocalDateTime value) {
    return A.localDateTime(success(value));
  }

  public static InstantVerifier that(Instant value) {
    return A.instant(success(value));
  }

  public static BooleanVerifier that(boolean value) {
    return A.bool(success(value));
  }

  public static <K, V, T extends Map<K, V>> MapVerifier<K, V, T> that(T value) {
    return A.map(success(value));
  }

  public static <K, V, M extends Dictionary<K, V>> DictionaryVerifier<K, V, M> that(M value) {
    return A.dictionary(success(value));
  }

  public static <V extends Comparable<V>> ComparableVerifier<V> that(V value) {
    return A.comparable(success(value));
  }

}
