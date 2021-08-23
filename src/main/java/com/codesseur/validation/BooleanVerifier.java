package com.codesseur.validation;

import io.vavr.Lazy;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class BooleanVerifier extends Verifier<Boolean, BooleanVerifier> {

  public static BooleanVerifier me(Lazy<? extends Boolean> value) {
    return ImmutableBooleanVerifier.builder().value(value).build();
  }

  public BooleanVerifier isTrue() {
    return satisfies(o -> o, () -> Failure.of("FALSE"));
  }

  public BooleanVerifier isFalse() {
    return satisfies(o -> !o, () -> Failure.of("FALSE"));
  }

}
