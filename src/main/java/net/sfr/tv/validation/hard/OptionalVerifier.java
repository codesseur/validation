package net.sfr.tv.validation.hard;

import java.util.Optional;
import java.util.function.Predicate;
import org.immutables.value.Value;

@Value.Immutable
public abstract class OptionalVerifier<T> extends
    GenericVerifier<Optional<T>, OptionalVerifier<T>> {

  public OrElse<Optional<T>> ifPresentSatisfies(Predicate<T> condition) {
    return satisfiesWithOptional(
        op -> op.map(v -> v.map(condition::test).orElse(true)).orElse(false));
  }

  @Override
  public OptionalVerifier<T> addContext(String key, Object value) {
    return ImmutableOptionalVerifier.<T>builder().from(this).context(context().add(key, value))
        .build();
  }

  @Override
  public OptionalVerifier<T> label(String label) {
    return ImmutableOptionalVerifier.<T>builder().from(this).label(label).build();
  }

}
