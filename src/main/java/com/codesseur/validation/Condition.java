package com.codesseur.validation;

import io.vavr.control.Either;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class Condition<V> {

  abstract Function<? super V, Either<Failures, V>> condition();

  Either<Failures, V> run(V value) {
    return condition().apply(value);
  }

  Condition<V> withLastFailure(BiFunction<? super V, ? super Failure, ? extends Failure> mapper) {
    return failure((v, fs) -> fs.mapLast(f -> mapper.apply(v, f)).collect(l -> ImmutableFailures.of(l, fs.context())));
  }

  Condition<V> failure(BiFunction<? super V, ? super Failures, ? extends Failures> failure) {
    return withCondition(v -> condition().apply(v).mapLeft(f -> failure.apply(v, f)));
  }

  abstract Condition<V> withCondition(Function<? super V, Either<Failures, V>> value);

}
