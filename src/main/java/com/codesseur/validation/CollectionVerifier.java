package com.codesseur.validation;

import io.vavr.Lazy;
import java.util.Collection;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class CollectionVerifier<E, C extends Collection<E>> extends
    AbstractCollectionVerifier<E, C, CollectionVerifier<E, C>> {

  public static <E, T extends Collection<E>> CollectionVerifier<E, T> me(Lazy<? extends T> value) {
    return ImmutableCollectionVerifier.<E, T>builder().value(value).build();
  }

}
