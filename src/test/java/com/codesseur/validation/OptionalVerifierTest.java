package com.codesseur.validation;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionalVerifierTest {

  @Test
  public void ifPresentSatisfiesWithMatching() {
    Verify.that(Optional.of("")).ifPresentSatisfies(String::isEmpty).otherwiseThrow();
  }

  @Test
  public void ifPresentSatisfiesWithEmpty() {
    Verify.that(Optional.<String>empty()).ifPresentSatisfies(String::isEmpty).otherwiseThrow();
  }

  @Test
  public void isEmptyWithEmpty() {
    Verify.that(Optional.<String>empty()).isEmpty().otherwiseThrow();
  }

  @Test
  public void isEmptyWithPresent() {
    Assertions.assertThatThrownBy(() -> Verify.that(Optional.of("")).isEmpty().otherwiseThrow())
        .isInstanceOf(ValidationException.class);
  }

  @Test
  public void isPresentWithPresent() {
    Verify.that(Optional.of("")).isPresent().otherwiseThrow();
  }

  @Test
  public void isPresentWithEmpty() {
    Assertions.assertThatThrownBy(() -> Verify.that(Optional.<String>empty()).isPresent().otherwiseThrow())
        .isInstanceOf(ValidationException.class);
  }
}