package com.codesseur.validation;

import com.codesseur.iterate.container.Sequence;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class SequenceVerifier<E, C extends Sequence<E>> extends
    AbstractContainerVerifier<E, List<E>, C, SequenceVerifier<E, C>> {

}
