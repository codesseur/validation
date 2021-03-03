package net.sfr.tv.validation.hard;

import org.immutables.value.Value;

@Value.Immutable
public abstract class ComparableVerifier<T extends Comparable<T>> extends
    GenericComparableVerifier<T, ComparableVerifier<T>> {

  @Override
  public ComparableVerifier<T> addContext(String key, Object value) {
    return ImmutableComparableVerifier.<T>builder().from(this).context(context().add(key, value)).build();
  }

  @Override
  public ComparableVerifier<T> label(String label) {
    return ImmutableComparableVerifier.<T>builder().from(this).label(label).build();
  }
}
