package com.codesseur.validation;

import com.codesseur.iterate.container.Sequence;
import io.vavr.Tuple2;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class Failures implements Sequence<Failure>, WithContext {

  public static Failures of(String code) {
    return of(Failure.of(code));
  }

  @SafeVarargs
  public static Failures of(String code, Tuple2<String, Object>... context) {
    return of(Failure.of(code, context));
  }

  public static Failures of(Failure failure) {
    return ImmutableFailures.builder().addValue(failure).build();
  }

  public Failures add(Failures failure) {
    return withValue(append(failure));
  }

  abstract Failures withValue(Iterable<? extends Failure> elements);

}
