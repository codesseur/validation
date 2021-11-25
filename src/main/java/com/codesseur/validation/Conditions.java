package com.codesseur.validation;

import static io.vavr.control.Either.right;
import static java.util.Collections.emptyList;

import com.codesseur.iterate.container.Sequence;
import io.vavr.control.Either;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Lazy;

@Immutable
public abstract class Conditions<V> implements Sequence<Condition<V>> {

  abstract Either<Failures, V> actual();

  public static <V> Conditions<V> success(V previous) {
    return ImmutableConditions.of(emptyList(), Either.right(previous));
  }

  public static <V> Conditions<V> empty(Either<Failures, V> previous) {
    return ImmutableConditions.of(emptyList(), previous);
  }

  @Lazy
  public Either<Failures, V> current() {
    return actual().flatMap(this::runCurrent);
  }

  private Either<Failures, V> runCurrent(V value) {
    return map(c -> c.run(value))
        .foldLeft(() -> right(value), (es, e) -> es.mapLeft(fs -> e.fold(fs::add, f -> fs)).flatMap(ss -> e));
  }

  public <W> Conditions<W> asPrevious(Function<? super V, ? extends W> mapper) {
    return ImmutableConditions.of(emptyList(), current().map(mapper));
  }

  Conditions<V> add(Condition<? extends V> condition) {
    return withValue(append((Condition<V>) condition));
  }

  Conditions<V> withLastFailure(BiFunction<? super V, ? super Failure, Failure> mapper) {
    return mapLast(c -> c.withLastFailure(mapper)).toListThen(this::withValue);
  }

  public Conditions<V> otherwiseThrow(Function<? super Failures, ? extends RuntimeException> otherwise) {
    current().getOrElseThrow(otherwise);
    return asPrevious(Function.identity());
  }

  abstract Conditions<V> withValue(Iterable<? extends Condition<V>> elements);

}
