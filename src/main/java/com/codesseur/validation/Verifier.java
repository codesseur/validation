package com.codesseur.validation;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.function.Function.identity;

import com.codesseur.iterate.Streamed;
import com.codesseur.reflect.Type;
import io.vavr.control.Either;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Verifier<V, S extends Verifier<V, S>> {

  abstract Conditions<V> conditions();

  public Either<Failures, V> get() {
    return conditions().current();
  }

  public <T, SS extends Verifier<T, SS>> SS isInstanceOf(Type<T> type,
      Function<? super Conditions<T>, ? extends SS> as) {
    return isInstanceOf(type).as(as);
  }

  public <T> ObjectVerifier<T> isInstanceOf(Type<T> type) {
    return satisfies(v -> type.safeCast(v).isPresent(), () -> Failures.of("NOT_EQUAL")).map(type::cast);
  }

  public S isEqualTo(V value) {
    return satisfies(v -> v.equals(value), () -> Failures.of("NOT_EQUAL"));
  }

  public S isNotEqualTo(V value) {
    return violates(v -> v.equals(value), () -> Failures.of("NOT_EQUAL"));
  }

  public S isNull() {
    return satisfies(Objects::isNull, () -> Failures.of("NOT_NULL"));
  }

  public S isNotNull() {
    return satisfies(Objects::nonNull, () -> Failures.of("NULL"));
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
    return satisfies(set::contains, () -> Failures.of("NOT_IN"));
  }

  public final S notIn(Iterable<? extends V> values) {
    Set<? extends V> set = Streamed.of(values).toSet();
    return violates(set::contains, () -> Failures.of("IN"));
  }

  public S violates(Predicate<? super V> condition, Supplier<Failures> failure) {
    return satisfies(condition.negate(), failure);
  }

  public S matches(Predicate<? super V> condition) {
    return satisfies(condition, () -> Failures.of("VALIDATION_ERROR"));
  }

  public S satisfies(Predicate<? super V> condition, Supplier<Failures> failure) {
    return satisfies(v -> condition.test(v) ? right(v) : left(failure.get()));
  }

  public S verify(Function<? super V, Verifier<?, ?>> condition) {
    return satisfies(v -> condition.apply(v).conditions().current().map(i -> v));
  }

  public S satisfies(Function<? super V, Either<Failures, V>> condition) {
    return withConditions(conditions().add(ImmutableCondition.of(condition)));
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

  public <SS extends Verifier<V, SS>> SS as(Function<? super Conditions<V>, ? extends SS> as) {
    return as.apply(conditions());
  }

  public S then() {
    return withConditions(conditions().asPrevious(identity()));
  }

  public <O> ObjectVerifier<O> map(Function<? super V, ? extends O> mapper) {
    return ImmutableObjectVerifier.of(conditions().asPrevious(mapper));
  }

  public <VV, SS extends Verifier<VV, SS>> SS mapAs(Function<? super V, ? extends VV> mapper,
      Function<? super Conditions<VV>, SS> as) {
    return this.<VV>map(mapper).as(as);
  }

  abstract S withConditions(Conditions<V> conditions);

}
