package com.codesseur.validation;

import java.time.Instant;
import java.util.function.Supplier;
import org.immutables.value.Value;

@Value.Immutable
public abstract class InstantVerifier extends
    AbstractTemporalVerifier<Instant, InstantVerifier> {

  Supplier<Instant> now() {
    return Instant::now;
  }

}
