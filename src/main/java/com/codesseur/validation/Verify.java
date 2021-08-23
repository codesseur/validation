package com.codesseur.validation;

import com.codesseur.iterate.container.Bag;
import com.codesseur.iterate.container.Dictionary;
import com.codesseur.iterate.container.Sequence;
import io.vavr.Lazy;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class Verify {

  public static StringVerifier that(String value) {
    return StringVerifier.me(Lazy.of(() -> value));
  }

  public static <E, T extends Collection<E>> CollectionVerifier<E, T> that(T value) {
    return CollectionVerifier.me(Lazy.of(() -> value));
  }

  public static <E, T extends Sequence<E>> SequenceVerifier<E, T> that(T value) {
    return SequenceVerifier.me(Lazy.of(() -> value));
  }

  public static <E, T extends Bag<E>> BagVerifier<E, T> that(T value) {
    return BagVerifier.me(Lazy.of(() -> value));
  }

  public static <T> ObjectVerifier<T> that(T value) {
    return ObjectVerifier.me(Lazy.of(() -> value));
  }

  public static <T> OptionalVerifier<T> that(Optional<T> value) {
    return OptionalVerifier.me(Lazy.of(() -> value));
  }

  public static LocalDateVerifier that(LocalDateTime value) {
    return LocalDateVerifier.me(Lazy.of(() -> value));
  }

  public static InstantVerifier that(Instant value) {
    return InstantVerifier.me(Lazy.of(() -> value));
  }

  public static BooleanVerifier that(boolean value) {
    return BooleanVerifier.me(Lazy.of(() -> value));
  }

  public static <K, V, T extends Map<K, V>> MapVerifier<K, V, T> that(T value) {
    return MapVerifier.me(Lazy.of(() -> value));
  }

  public static <K, V, M extends Dictionary<K, V>> DictionaryVerifier<K, V, M> that(M value) {
    return DictionaryVerifier.me(Lazy.of(() -> value));
  }

  public static <V extends Comparable<V>> ComparableVerifier<V> that(V value) {
    return ComparableVerifier.me(Lazy.of(() -> value));
  }

}
