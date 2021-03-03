package net.sfr.tv.validation.hard;

import org.immutables.value.Value;

@Value.Immutable
public abstract class ObjectVerifier<T> extends GenericVerifier<T, ObjectVerifier<T>> {

  @Override
  public ObjectVerifier<T> addContext(String key, Object value) {
    return ImmutableObjectVerifier.<T>builder().from(this).context(context().add(key, value)).build();
  }

  @Override
  public ObjectVerifier<T> label(String label) {
    return ImmutableObjectVerifier.<T>builder().from(this).label(label).build();
  }
}
