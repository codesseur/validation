package com.codesseur.validation;

import java.util.Optional;
import java.util.function.Predicate;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class OptionalVerifier<T> extends Verifier<Optional<T>, OptionalVerifier<T>> {

  public OptionalVerifier<T> ifPresentSatisfies(Predicate<? super T> condition) {
    return isNotNull().then().satisfies(v -> v.map(condition::test).orElse(true), () -> Failures.of("NOT_SATISFY"));
  }

  public OptionalVerifier<T> isEmpty() {
    return isNotNull().then().satisfies(Optional::isEmpty, () -> Failures.of("PRESENT"));
  }

  public OptionalVerifier<T> isPresent() {
    return isNotNull().then().satisfies(Optional::isPresent, () -> Failures.of("EMPTY"));
  }

}
