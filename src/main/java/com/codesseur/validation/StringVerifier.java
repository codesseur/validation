package com.codesseur.validation;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.vavr.Tuple;
import java.util.regex.Pattern;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class StringVerifier extends Verifier<String, StringVerifier> {

  public StringVerifier isEmpty() {
    return satisfies(String::isEmpty, () -> Failures.of("NOT_EMPTY"));
  }

  public StringVerifier isNotEmpty() {
    return violates(String::isEmpty, () -> Failures.of("EMPTY"));
  }

  public StringVerifier isNotBlank() {
    return violates(s -> s.trim().isEmpty(), () -> Failures.of("BLANK"));
  }

  public StringVerifier matchesCaseSensitive(String regex) {
    return matches(Pattern.compile(regex));
  }

  public StringVerifier matchesCaseInsensitive(String regex) {
    return matches(Pattern.compile(regex, CASE_INSENSITIVE));
  }

  private StringVerifier matches(Pattern pattern) {
    return satisfies(o -> pattern.matcher(o).find(), () -> Failures.of("FALSE", Tuple.of("pattern", pattern)));
  }
}
