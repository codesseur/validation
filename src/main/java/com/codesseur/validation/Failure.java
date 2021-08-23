package com.codesseur.validation;

import io.vavr.Tuple2;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class Failure implements WithContext {

  abstract String code();

  abstract Failure withCode(String code);

  public static Failure of(String code) {
    return ImmutableFailure.of(Context.empty(), code);
  }

  @SafeVarargs
  public static Failure of(String code, Tuple2<String, Object>... context) {
    return ImmutableFailure.of(Context.of(context), code);
  }

}
