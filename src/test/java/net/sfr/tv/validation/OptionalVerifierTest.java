package net.sfr.tv.validation;

import static net.sfr.tv.validation.ToBe.invalid;
import static net.sfr.tv.validation.condition.OptionalConditions.IfPresent;

import java.util.Optional;
import org.junit.jupiter.api.Test;

public class OptionalVerifierTest {

  @Test
  public void ifPresentSatisfiesWithMatching() {
    Verify.that(Optional.of("")).satisfies(IfPresent(String::isEmpty), invalid("")).orElseThrow();
  }

  @Test
  public void ifPresentSatisfiesWithEmpty() {
    Verify.that(Optional.<String>empty()).satisfies(IfPresent(String::isEmpty), invalid("")).orElseThrow();
  }
}