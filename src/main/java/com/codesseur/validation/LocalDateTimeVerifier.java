package com.codesseur.validation;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.function.Supplier;
import org.immutables.value.Value;

@Value.Immutable
public abstract class LocalDateTimeVerifier extends
    AbstractTemporalVerifier<ChronoLocalDateTime<?>, LocalDateTimeVerifier> {

  Supplier<ChronoLocalDateTime<?>> now() {
    return LocalDateTime::now;
  }

}
