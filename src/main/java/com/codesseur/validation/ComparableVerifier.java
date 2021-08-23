package com.codesseur.validation;

import io.vavr.Lazy;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class ComparableVerifier<V extends Comparable<V>> extends
    AbstractComparableVerifier<V, ComparableVerifier<V>> {

  public static <V extends Comparable<V>> ComparableVerifier<V> me(Lazy<? extends V> value) {
    return ImmutableComparableVerifier.<V>builder().value(value).build();
  }
}
