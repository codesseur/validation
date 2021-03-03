package net.sfr.tv.validation.hard;

import org.immutables.value.Value;

@Value.Immutable
public abstract class BooleanVerifier extends GenericVerifier<Boolean, BooleanVerifier> {

  public OrElse<Boolean> isTrue() {
    return satisfies(o -> o);
  }

  public OrElse<Boolean> isFalse() {
     return satisfies(o -> !o);
  }

  @Override
  public BooleanVerifier addContext(String key, Object value) {
    return ImmutableBooleanVerifier.builder().from(this).context(context().add(key, value)).build();
  }

  @Override
  public BooleanVerifier label(String label) {
    return ImmutableBooleanVerifier.builder().from(this).label(label).build();
  }
}
