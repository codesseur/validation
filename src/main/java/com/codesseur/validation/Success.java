package com.codesseur.validation;

import org.immutables.value.Value.Immutable;

@Immutable
public abstract class Success implements WithContext {

  public static Success empty() {
    return ImmutableSuccess.builder().build();
  }

  public Success add(Success other) {
    return ImmutableSuccess.builder().from(this).context(context().merge(other.context())).build();
  }
}
