package net.sfr.tv.validation;

import static net.sfr.tv.validation.ToBe.invalid;
import static net.sfr.tv.validation.condition.ComparableConditions.GreaterThan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyComparableTest {

  @Test
  public void oneGreaterThanTwo() {
    Assertions.assertThatThrownBy(() -> Verify.that(1).is(GreaterThan(2), invalid("INVALID"))
        .orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

}