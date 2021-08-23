package com.codesseur.validation;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.vavr.Lazy;
import io.vavr.Tuple;
import java.util.regex.Pattern;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class StringVerifier extends Verifier<String, StringVerifier> {

  public static StringVerifier me(Lazy<? extends String> value) {
    return ImmutableStringVerifier.builder().value(value).build();
  }

  public StringVerifier isEmpty() {
    return satisfies(String::isEmpty, () -> Failure.of("NOT_EMPTY"));
  }

  public StringVerifier isNotEmpty() {
    return violates(String::isEmpty, () -> Failure.of("EMPTY"));
  }

  public StringVerifier isNotBlank() {
    return violates(s -> s.trim().isEmpty(), () -> Failure.of("BLANK"));
  }

  public StringVerifier matchesCaseSensitive(String regex) {
    return matches(Pattern.compile(regex));
  }

  public StringVerifier matchesCaseInsensitive(String regex) {
    return matches(Pattern.compile(regex, CASE_INSENSITIVE));
  }

  private StringVerifier matches(Pattern pattern) {
    return satisfies(o -> pattern.matcher(o).find(), () -> Failure.of("FALSE", Tuple.of("pattern", pattern)));
  }
}
