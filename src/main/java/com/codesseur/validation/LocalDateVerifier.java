package com.codesseur.validation;

import io.vavr.Lazy;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.function.Supplier;
import org.immutables.value.Value;

@Value.Immutable
public abstract class LocalDateVerifier extends
    AbstractTemporalVerifier<ChronoLocalDateTime<?>, LocalDateVerifier> {

  public static LocalDateVerifier me(Lazy<? extends LocalDateTime> value) {
    return ImmutableLocalDateVerifier.builder().value(value).build();
  }

  Supplier<ChronoLocalDateTime<?>> now() {
    return LocalDateTime::now;
  }

}
