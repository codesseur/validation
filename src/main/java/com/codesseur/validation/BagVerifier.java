package com.codesseur.validation;

import com.codesseur.iterate.container.Bag;
import io.vavr.control.Either;
import java.util.Set;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class BagVerifier<E, C extends Bag<E>> extends
    AbstractContainerVerifier<E, Set<E>, C, BagVerifier<E, C>> {



}
