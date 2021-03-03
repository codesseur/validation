package net.sfr.tv.validation.hard;

import org.immutables.value.Value;

@Value.Immutable
interface Success {

  Success INSTANCE = ImmutableSuccess.builder().build();
}
