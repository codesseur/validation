package com.codesseur.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyStringTest {

  @Test
  public void emptyValidationWithEmptyString() {
    Verify.that("").isEmpty().otherwiseThrow();
  }

  @Test
  public void emptyWithNoneEmptyString() {
    Assertions.assertThatThrownBy(() -> Verify.that("bla").isEmpty().otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void notEmptyValidationWithEmptyString() {
    Assertions.assertThatThrownBy(() -> Verify.that("").isNotEmpty().otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void nonEmptyString() {
    Verify.that("blabla").isNotEmpty().otherwiseThrow();
  }

  @Test
  public void blankString() {
    Assertions.assertThatThrownBy(() -> Verify.that("   ").isNotBlank().otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void nonBlankString() {
    Verify.that("blabla").isNotBlank().otherwiseThrow();
  }

  @Test
  public void nonMatchingStringCaseSensitive() {
    Assertions.assertThatThrownBy(() -> Verify.that("blabla").matchesCaseSensitive("BLA.*").otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void matchingStringCaseSensitive() {
    Verify.that("blabla").matchesCaseSensitive("blabla").otherwiseThrow();
  }

  @Test
  public void nonMatchingStringCaseInsensitive() {
    Assertions.assertThatThrownBy(
        () -> Verify.that("blabla").matchesCaseInsensitive("m.*").otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void matchingStringCaseInsensitive() {
    Verify.that("blabla").matchesCaseInsensitive("blabla").otherwiseThrow();
  }

  @Test
  public void matchingStringCaseInsensitiveUpperCase() {
    Verify.that("blabla").matchesCaseInsensitive("BLABLA").otherwiseThrow();
  }

}