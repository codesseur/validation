package com.codesseur.validation;

import com.codesseur.iterate.container.Sequence;
import io.vavr.Lazy;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class SequenceVerifier<E, C extends Sequence<E>> extends
    AbstractContainerVerifier<E, List<E>, C, SequenceVerifier<E, C>> {

  public static <E, C extends Sequence<E>> SequenceVerifier<E, C> me(Lazy<? extends C> value) {
    return ImmutableSequenceVerifier.<E, C>builder().value(value).build();
  }

}
