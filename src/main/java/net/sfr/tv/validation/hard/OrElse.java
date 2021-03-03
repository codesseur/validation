package net.sfr.tv.validation.hard;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.sfr.tv.ImmutablesInvalidValue;
import net.sfr.tv.validation.Context;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class OrElse<VALUE> {

  abstract Optional<VALUE> value();

  abstract Status result();

  public void orElseThrow() {
    orElseThrowWithValue(value -> new ImmutablesInvalidValue("invalid value : " + value));
  }

  public void orElseThrow(Supplier<? extends RuntimeException> exceptionFactory) {
    orElseThrow(ignored -> exceptionFactory.get());
  }

  public void orElseThrowWithOptionalValue(
      Function<Optional<VALUE>, ? extends RuntimeException> exceptionFactory) {
    orElseThrow(message -> exceptionFactory.apply(value()));
  }

  public void orElseThrowWithValue(Function<VALUE, ? extends RuntimeException> exceptionFactory) {
    orElseThrowWithOptionalValue(v -> exceptionFactory.apply(v.orElse(null)));
  }

  private void orElseThrow(Function<? super Context, ? extends RuntimeException> exceptionFactory) {
    if (result().isKo()) {
      throw exceptionFactory.apply(result().context());
    }
  }

  public <V> Optional<V> orElseGet(Supplier<V> factory) {
    return orElseGet(ignored -> factory.get());
  }

  public <V> Optional<V> orElseGetWithOptionalValue(Function<Optional<VALUE>, V> factory) {
    return orElseGet(message -> factory.apply(value()));
  }

  public <V> Optional<V> orElseGetWithValue(Function<VALUE, V> factory) {
    return orElseGetWithOptionalValue(v -> factory.apply(v.orElse(null)));
  }

  public boolean isValid() {
    return orElseGet(() -> false).orElse(true);
  }

  public boolean isInvalid() {
    return !isValid();
  }

  private <V> Optional<V> orElseGet(Function<Context, V> factory) {
    return result().isKo() ? Optional.ofNullable(factory.apply(result().context())) : Optional.empty();
  }
}
