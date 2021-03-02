package net.sfr.tv.validation;

import static net.sfr.tv.validation.ToBe.invalid;
import static net.sfr.tv.validation.condition.StringConditions.MatchCaseInsensitive;
import static net.sfr.tv.validation.condition.StringConditions.MatchCaseSensitive;
import static net.sfr.tv.validation.condition.StringConditions.NotBlank;
import static net.sfr.tv.validation.condition.StringConditions.NotEmpty;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyStringTest {

  @Test
  public void notEmptyValidationWithEmptyString() {
    Assertions.assertThatThrownBy(() -> Verify.that("").is(NotEmpty(), invalid("INVALID")).orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void nonEmptyString() {
    Verify.that("blabla").is(NotEmpty(), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void blankString() {
    Assertions.assertThatThrownBy(() -> Verify.that("   ").is(NotBlank(), invalid("INVALID")).orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void nonBlankString() {
    Verify.that("blabla").is(NotBlank(), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void nonMatchingStringCaseSensitive() {
    Assertions.assertThatThrownBy(
        () -> Verify.that("blabla").is(MatchCaseSensitive("BLA.*")
            , invalid("INVALID")).orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void matchingStringCaseSensitive() {
    Verify.that("blabla").is(MatchCaseSensitive("blabla")
        , invalid("INVALID")).orElseThrow();
  }

  @Test
  public void nonMatchingStringCaseInsensitive() {
    Assertions.assertThatThrownBy(
        () -> Verify.that("blabla").is(MatchCaseInsensitive("m.*")
            , invalid("INVALID")).orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void matchingStringCaseInsensitive() {
    Verify.that("blabla").is(MatchCaseInsensitive("blabla")
        , invalid("INVALID")).orElseThrow();
  }

  @Test
  public void matchingStringCaseInsensitiveUpperCase() {
    Verify.that("blabla").is(MatchCaseInsensitive("BLABLA")
        , invalid("INVALID")).orElseThrow();
  }

}