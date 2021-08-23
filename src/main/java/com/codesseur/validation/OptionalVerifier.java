package com.codesseur.validation;

import io.vavr.Lazy;
import java.util.Optional;
import java.util.function.Predicate;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class OptionalVerifier<T> extends Verifier<Optional<T>, OptionalVerifier<T>> {

  public static <T> OptionalVerifier<T> me(Lazy<? extends Optional<T>> value) {
    return ImmutableOptionalVerifier.<T>builder().value(value).build();
  }

  public OptionalVerifier<T> ifPresentSatisfies(Predicate<? super T> condition) {
    return isNotNull().then().satisfies(v -> v.map(condition::test).orElse(true), () -> Failure.of("NOT_SATISFY"));
  }

  public OptionalVerifier<T> isEmpty() {
    return isNotNull().then().satisfies(Optional::isEmpty, () -> Failure.of("PRESENT"));
  }

  public OptionalVerifier<T> isPresent() {
    return isNotNull().then().satisfies(Optional::isPresent, () -> Failure.of("EMPTY"));
  }

}
