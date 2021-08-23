package com.codesseur.validation;

import com.codesseur.MicroType;
import io.vavr.Lazy;
import io.vavr.control.Either;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class Condition<V> implements MicroType<Lazy<V>> {

  abstract Function<? super V, Either<Failure, Success>> condition();

  Either<Failure, Success> run() {
    return condition().apply(value().get());
  }

  Condition<V> failure(BiFunction<? super V, ? super Failure, Failure> failure) {
    return withCondition(v -> condition().apply(v).mapLeft(f -> failure.apply(v, f)));
  }

  abstract Condition<V> withCondition(Function<? super V, Either<Failure, Success>> value);

}
