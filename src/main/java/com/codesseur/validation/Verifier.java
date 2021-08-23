package com.codesseur.validation;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import com.codesseur.iterate.Streamed;
import io.vavr.Lazy;
import io.vavr.control.Either;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.immutables.value.Value.Default;

public abstract class Verifier<V, S> {

  abstract Lazy<? extends V> value();

  @Default
  public Conditions<V> conditions() {
    return Conditions.empty();
  }

  public V get() {
    return value().get();
  }

  public S isEqualTo(V value) {
    return satisfies(v -> v.equals(value), () -> Failure.of("NOT_EQUAL"));
  }

  public S isNotEqualTo(V value) {
    return violates(v -> v.equals(value), () -> Failure.of("NOT_EQUAL"));
  }

  public S isNull() {
    return satisfies(Objects::isNull, () -> Failure.of("NOT_NULL"));
  }

  public S isNotNull() {
    return satisfies(Objects::nonNull, () -> Failure.of("NULL"));
  }

  @SafeVarargs
  public final S isIn(V... values) {
    return isIn(Arrays.asList(values));
  }

  @SafeVarargs
  public final S notIn(V... values) {
    return notIn(Arrays.asList(values));
  }

  public final S isIn(Iterable<? extends V> values) {
    Set<? extends V> set = Streamed.of(values).toSet();
    return satisfies(set::contains, () -> Failure.of("NOT_IN"));
  }

  public final S notIn(Iterable<? extends V> values) {
    Set<? extends V> set = Streamed.of(values).toSet();
    return violates(set::contains, () -> Failure.of("IN"));
  }

  public S violates(Predicate<? super V> condition, Supplier<Failure> failure) {
    return satisfies(condition.negate(), failure);
  }

  public S matches(Predicate<? super V> condition) {
    return satisfies(condition, () -> Failure.of("VALIDATION_ERROR"));
  }

  public S satisfies(Predicate<? super V> condition, Supplier<Failure> failure) {
    return satisfies(v -> condition.test(v) ? right(Success.empty()) : left(failure.get()));
  }

  public S satisfies(Function<? super V, Either<Failure, Success>> condition) {
    return withConditions(conditions().add(ImmutableCondition.of(value(), condition)));
  }

  public S otherwise(String code) {
    return otherwise(i -> code);
  }

  public S otherwise(Function<? super V, String> code) {
    return otherwise((v, f) -> f.withCode(code.apply(v)));
  }

  public S otherwise(BiFunction<? super V, ? super Failure, Failure> failureMapper) {
    return withConditions(conditions().withLastFailure(failureMapper));
  }

  public S otherwiseThrowLast(Function<Failure, ? extends RuntimeException> otherwise) {
    return otherwiseThrow(f -> f.last().map(otherwise).orElseThrow());
  }

  public S otherwiseThrow() {
    return otherwiseThrow(ValidationException::new);
  }

  public S otherwiseThrow(Supplier<? extends RuntimeException> otherwise) {
    return withConditions(conditions().otherwiseThrow(f -> otherwise.get()));
  }

  public S otherwiseThrow(Function<? super Failures, ? extends RuntimeException> otherwise) {
    return withConditions(conditions().otherwiseThrow(otherwise));
  }

  public <SS extends Verifier<V, SS>> SS flatMap(Function<? super Lazy<? extends V>, ? extends SS> as) {
    return as.apply(value());
  }

  public S then() {
    return withConditions(conditions().asPrevious());
  }

  public <O> ObjectVerifier<O> map(Function<? super V, ? extends O> mapper) {
    return ImmutableObjectVerifier.of(value().map(mapper), conditions().asPrevious());
  }

  public <VV, SS extends Verifier<VV, SS>> SS map(Function<? super V, ? extends VV> mapper,
      Function<? super Lazy<? extends VV>, SS> as) {
    return this.<VV>map(mapper).flatMap(as);
  }

  abstract S withConditions(Conditions<V> conditions);

}
