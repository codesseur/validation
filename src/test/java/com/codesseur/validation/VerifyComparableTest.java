package com.codesseur.validation;

import com.codesseur.validation.Verify;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyComparableTest {

  @Test
  public void oneGreaterThanTwo() {
    Assertions.assertThatThrownBy(() -> Verify.that(1).isGreaterThan(2)
        .otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void oneLessThan2() {
    Verify.that(1).isLessThan(2).otherwiseThrow();
  }

  @Test
  public void oneIsLessThanOrEqualOne() {
    Verify.that(1).isLessThanOrEqual(1).otherwiseThrow();
  }

  @Test
  public void oneIsGreaterThanOrEqualOne() {
    Verify.that(1).isGreaterThanOrEqual(1).otherwiseThrow();
  }

  @Test
  public void oneIsBetweenZeroAndTwo() {
    Verify.that(1).isBetween(0, 2).otherwiseThrow();
  }

  @Test
  public void oneIsBetweenTwoAndThree() {
    Assertions.assertThatThrownBy(() -> Verify.that(1).isBetween(2, 3)
        .otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

}