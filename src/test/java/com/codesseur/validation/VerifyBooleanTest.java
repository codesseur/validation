package com.codesseur.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyBooleanTest {

  @Test
  public void trueIsFalse() {
    Assertions.assertThatThrownBy(() -> Verify.that(true).isFalse().otherwiseThrow())
        .isInstanceOf(ValidationException.class);
  }

  @Test
  public void trueIsTrue() {
    Verify.that(true).isTrue().otherwiseThrow();
  }

  @Test
  public void falseIsTrue() {
    Assertions.assertThatThrownBy(() -> Verify.that(false).isTrue().otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void falseIsFalse() {
    Verify.that(false).isFalse().otherwiseThrow();
  }

}