package com.codesseur.validation;

import io.vavr.Lazy;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class ObjectVerifier<V> extends Verifier<V, ObjectVerifier<V>> {

  public static <T> ObjectVerifier<T> me(Lazy<? extends T> value) {
    return ImmutableObjectVerifier.<T>builder().value(value).build();
  }
}
