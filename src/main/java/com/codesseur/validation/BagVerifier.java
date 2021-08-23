package com.codesseur.validation;

import com.codesseur.iterate.container.Bag;
import io.vavr.Lazy;
import java.util.Set;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class BagVerifier<E, C extends Bag<E>> extends
    AbstractContainerVerifier<E, Set<E>, C, BagVerifier<E, C>> {

  public static <E, C extends Bag<E>> BagVerifier<E, C> me(Lazy<? extends C> value) {
    return ImmutableBagVerifier.<E, C>builder().value(value).build();
  }

}
