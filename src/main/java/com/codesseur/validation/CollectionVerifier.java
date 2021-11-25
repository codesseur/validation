package com.codesseur.validation;

import java.util.Collection;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class CollectionVerifier<E, C extends Collection<E>> extends
    AbstractCollectionVerifier<E, C, CollectionVerifier<E, C>> {

}
