package net.sfr.tv.validation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import net.sfr.tv.mixin.iterate.container.CollectionContainer;
import net.sfr.tv.mixin.iterate.container.Dictionary;
import net.sfr.tv.validation.hard.BooleanVerifier;
import net.sfr.tv.validation.hard.CollectionVerifier;
import net.sfr.tv.validation.hard.ComparableVerifier;
import net.sfr.tv.validation.hard.ImmutableBooleanVerifier;
import net.sfr.tv.validation.hard.ImmutableCollectionVerifier;
import net.sfr.tv.validation.hard.ImmutableComparableVerifier;
import net.sfr.tv.validation.hard.ImmutableMapVerifier;
import net.sfr.tv.validation.hard.ImmutableObjectVerifier;
import net.sfr.tv.validation.hard.ImmutableOptionalVerifier;
import net.sfr.tv.validation.hard.ImmutableStringVerifier;
import net.sfr.tv.validation.hard.ImmutableTemporalVerifier;
import net.sfr.tv.validation.hard.MapVerifier;
import net.sfr.tv.validation.hard.ObjectVerifier;
import net.sfr.tv.validation.hard.OptionalVerifier;
import net.sfr.tv.validation.hard.StringVerifier;
import net.sfr.tv.validation.hard.TemporalVerifier;

public class Verify {

  private Verify() {
  }

  public static StringVerifier that(String value) {
    return ImmutableStringVerifier.builder().value(value).build();
  }

  public static <E, T extends Collection<E>> CollectionVerifier<E, T> that(T value) {
    return ImmutableCollectionVerifier.<E, T>builder().value(value).build();
  }

  public static <E, T extends Collection<E>, C extends CollectionContainer<E, T>> CollectionVerifier<E, T> that(
      C value) {
    return ImmutableCollectionVerifier.<E, T>builder().value(value.value()).build();
  }

  public static <T> ObjectVerifier<T> that(T value) {
    return ImmutableObjectVerifier.<T>builder().value(value).build();
  }

  public static <T> OptionalVerifier<T> that(Optional<T> value) {
    return ImmutableOptionalVerifier.<T>builder().value(Optional.ofNullable(value)).build();
  }

  public static <T extends Comparable<T>> ComparableVerifier<T> that(T value) {
    return ImmutableComparableVerifier.<T>builder().value(value).build();
  }

  public static TemporalVerifier<ChronoLocalDateTime<?>> that(LocalDateTime value) {
    return ImmutableTemporalVerifier.<ChronoLocalDateTime<?>>builder().value(value)
        .now(LocalDateTime::now).build();
  }

  public static TemporalVerifier<Instant> that(Instant value) {
    return ImmutableTemporalVerifier.<Instant>builder().value(value).now(Instant::now).build();
  }

  public static BooleanVerifier that(boolean value) {
    return ImmutableBooleanVerifier.builder().value(value).build();
  }

  public static <K, V, T extends Map<K, V>> MapVerifier<K, V, T> that(T value) {
    return ImmutableMapVerifier.<K, V, T>builder().value(value).build();
  }

  public static <K, V, M extends Dictionary<K, V>> MapVerifier<K, V, Map<K, V>> that(M value) {
    return ImmutableMapVerifier.<K, V, Map<K, V>>builder().value(value.value()).build();
  }
}
