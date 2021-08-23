package com.codesseur.validation;

import io.vavr.Lazy;
import java.time.Instant;
import java.util.function.Supplier;
import org.immutables.value.Value;

@Value.Immutable
public abstract class InstantVerifier extends
    AbstractTemporalVerifier<Instant, InstantVerifier> {

  public static InstantVerifier me(Lazy<? extends Instant> value) {
    return ImmutableInstantVerifier.builder().value(value).build();
  }

  Supplier<Instant> now() {
    return Instant::now;
  }

}
