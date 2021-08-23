package com.codesseur.validation;

import org.immutables.value.Value.Default;

public interface WithContext {

  @Default
  default Context context() {
    return Context.empty();
  }
}
