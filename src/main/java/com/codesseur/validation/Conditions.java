package com.codesseur.validation;

import static io.vavr.control.Either.right;
import static java.util.Collections.emptyList;

import com.codesseur.iterate.container.Sequence;
import io.vavr.Lazy;
import io.vavr.control.Either;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

@Immutable
public abstract class Conditions<V> implements Sequence<Condition<V>> {

  public static <V> Conditions<V> empty() {
    return ImmutableConditions.of(emptyList(), Lazy.of(() -> right(Success.empty())));
  }

  abstract Lazy<Either<Failures, Success>> previous();

  @Derived
  public Lazy<Either<Failures, Success>> current() {
    return Lazy.of(() -> previous().get().flatMap(this::runCurrent));
  }

  private Either<Failures, Success> runCurrent(Success success) {
    return map(Condition::run).foldLeft(
        right(success),
        (es, e) -> es.mapLeft(fs -> e.fold(fs::add, fs::add)).flatMap(ss -> e.bimap(Failures::of, ss::add)));
  }

  public <W> Conditions<W> asPrevious() {
    return ImmutableConditions.of(emptyList(), current());
  }

  Conditions<V> add(Condition<? extends V> condition) {
    return withValue(append((Condition<V>) condition));
  }

  Conditions<V> withLastFailure(BiFunction<? super V, ? super Failure, Failure> failureMapper) {
    return mapLast(c -> c.failure(failureMapper)).toListThen(this::withValue);
  }

  public <W> Conditions<W> otherwiseThrow(Function<? super Failures, ? extends RuntimeException> otherwise) {
    current().get().getOrElseThrow(otherwise);
    return asPrevious();
  }

  abstract Conditions<V> withValue(Iterable<? extends Condition<V>> elements);

}
