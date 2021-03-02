package net.sfr.tv.validation;

import static net.sfr.tv.validation.ToBe.invalid;
import static net.sfr.tv.validation.condition.BooleanConditions.False;
import static net.sfr.tv.validation.condition.BooleanConditions.True;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyBooleanTest {

  @Test
  public void trueIsFalse() {
    Assertions.assertThatThrownBy(() -> Verify.that(true).is(False(), invalid("INVALID")).orElseThrow())
        .isInstanceOf(ValidationException.class);
  }

  @Test
  public void trueIsTrue() {
    Verify.that(true).is(True(), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void falseIsTrue() {
    Assertions.assertThatThrownBy(() -> Verify.that(false).is(True(), invalid("INVALID")).orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void falseIsFalse() {
    Verify.that(false).is(False(), invalid("INVALID")).orElseThrow();
  }

}