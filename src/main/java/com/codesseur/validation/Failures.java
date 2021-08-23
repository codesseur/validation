package com.codesseur.validation;

import com.codesseur.iterate.container.Sequence;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class Failures implements Sequence<Failure>, WithContext {

  public static Failures of(Failure failure) {
    return ImmutableFailures.builder().addValue(failure).build();
  }

  public Failures add(Failure failure) {
    return withValue(append(failure));
  }

  public Failures add(WithContext context) {
    return withContext(context().merge(context.context()));
  }

  abstract Failures withValue(Iterable<? extends Failure> elements);

  abstract Failures withContext(Context value);

}
