package com.codesseur.validation;

import io.vavr.control.Either;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class BooleanVerifier extends Verifier<Boolean, BooleanVerifier> {

  public BooleanVerifier isTrue() {
    return satisfies(o -> o, () -> Failures.of("FALSE"));
  }

  public BooleanVerifier isFalse() {
    return satisfies(o -> !o, () -> Failures.of("FALSE"));
  }

}
