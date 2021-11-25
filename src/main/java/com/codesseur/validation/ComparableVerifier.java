package com.codesseur.validation;

import org.immutables.value.Value.Immutable;

@Immutable
public abstract class ComparableVerifier<V extends Comparable<V>> extends
    AbstractComparableVerifier<V, ComparableVerifier<V>> {

}
