package net.sfr.tv.validation.hard;

import java.util.Collection;
import org.immutables.value.Value;

@Value.Immutable
public abstract class CollectionVerifier<E, T extends Collection<E>> extends
    GenericCollectionVerifier<E, T, CollectionVerifier<E, T>> {

  @Override
  public CollectionVerifier<E, T> addContext(String key, Object value) {
    return ImmutableCollectionVerifier.<E, T>builder().from(this).context(context().add(key, value)).build();
  }

  @Override
  public CollectionVerifier<E, T> label(String label) {
    return ImmutableCollectionVerifier.<E, T>builder().from(this).label(label).build();
  }

}
