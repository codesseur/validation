package net.sfr.tv.validation;

import static java.util.function.Function.identity;
import static net.sfr.tv.mixin.functions.Functions.functionize;

import io.vavr.CheckedFunction1;
import io.vavr.collection.Queue;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.sfr.tv.mixin.Optionals;
import org.immutables.value.Value;

@Value.Immutable
public interface Validated<T> {

  Validation<Failures, T> validation();

  default  <E> Validated<E> map(Function<? super T, ? extends E> mapper) {
    return ImmutableValidated.of(validation().map(mapper));
  }

  default <E> Validated<E> map(CheckedFunction1<? super T, ? extends E> mapper, Supplier<? extends Validated<E>> orElse) {
    return validation()
        .fold(Is::invalid, o -> Try.of(() -> mapper.apply(o)).<Validated<E>>map(Is::valid).getOrElse(orElse));
  }

  default <E> Validated<E> mapPartial(Function<? super T, ? extends Optional<E>> mapper, Supplier<? extends Validated<E>> orElse) {
    return validation().fold(
        Is::invalid, mapper.andThen(o -> o.map(Is::valid).orElseGet(orElse)));
  }

  default <E> Validated<E> flatMap(Function<? super T, ? extends Validated<E>> mapper) {
    return validation().fold(Is::invalid, mapper);
  }

  default <E, V> Validated<Stream<V>> flatMap(Function<? super T, ? extends Stream<E>> converter, Function<? super E, ? extends Validated<V>> mapper) {
    return flatMap(v -> converter.apply(v)
        .map(mapper)
        .map(validated -> validated.map(Queue::of))
        .reduce((v1, v2) -> v1.merge(v2, Queue::appendAll))
        .orElse(Is.valid(Queue.empty())))
        .map(Queue::toJavaStream);
  }

  default Validated<T> isNotNull(Supplier<? extends Validated<T>> orElse) {
    return filterPredicate(Objects::nonNull, orElse);
  }

  default Validated<T> filterIfPresent(Predicate<? super T> condition, Supplier<? extends Validated<T>> orElse) {
    return filterPredicate(o -> Optional.of(o).map(condition::test).orElse(true), orElse);
  }

  default Validated<T> filterPredicate(Predicate<? super T> condition, Supplier<? extends Validated<T>> orElse) {
    return filterPredicate(condition, t -> orElse.get());
  }

  default Validated<T> filterPredicate(Predicate<? super T> condition, Function<? super T, ? extends Validated<T>> orElse) {
    return filter(t -> Status.withoutContext(condition.test(t)), orElse);
  }

  default Validated<T> filter(Function<? super T, ? extends Status> condition, Supplier<? extends Validated<T>> orElse) {
    return filter(condition, i -> orElse.get());
  }

  default Validated<T> filter(Function<? super T, ? extends Status> condition, Function<? super T, ? extends Validated<T>> orElse) {
    return validation().fold(Is::invalid,
        t -> {
          Status status = condition.apply(t);
          return status.isOk() ? Is.valid(t) : orElse.apply(t).addToContext(status.context());
        });
  }

  default View<T> is(Function<? super T, ? extends Status> condition, Supplier<? extends Validated<T>> orElse) {
    return satisfies(condition, i -> orElse.get());
  }

  default View<T> is(Function<? super T, ? extends Status> condition, Function<? super T, ? extends Validated<T>> orElse) {
    return satisfies(condition, orElse);
  }

  default View<T> satisfies(Function<? super T, ? extends Status> condition, Supplier<? extends Validated<T>> orElse) {
    return satisfies(condition, i -> orElse.get());
  }

  default View<T> satisfies(Function<? super T, ? extends Status> condition, Function<? super T, ? extends Validated<T>> orElse) {
    return filter(condition, orElse).view();
  }

  default View<T> view() {
    return new View<>(this);
  }

  default T fold(Function<? super Failures, ? extends T> failuresMapper) {
    return fold(failuresMapper, identity());
  }

  default <E> E fold(Function<? super Failures, ? extends E> failuresMapper, Function<? super T, ? extends E> valueMapper) {
    return validation().fold(failuresMapper, valueMapper);
  }

  default Validated<T> merge(Validated<T> validated, BinaryOperator<T> merger) {
    return ImmutableValidated.of(validation().combine(validated.validation()).ap(merger::apply)
        .mapError(s -> s.reduce(Failures::add)));
  }

  default Validated<T> peekError(Consumer<? super Failures> consumer) {
    return ImmutableValidated.of(validation().mapError(functionize(consumer)));
  }

  default Validated<T> peek(Consumer<? super T> consumer) {
    return ImmutableValidated.of(validation().peek(consumer));
  }

  default Validated<T> addToContext(String key, Object value) {
    return mapFailure(f -> f.addToContext(key, value));
  }

  default Validated<T> addToContext(Context context) {
    return mapFailure(f -> f.addToContext(context));
  }

  default Validated<T> ifInvalidVerify(Supplier<? extends Validated<?>> toVerify) {
    return withFailures(toVerify.get().validation().fold(identity(), i -> Failures.empty()));
  }

  default Validated<T> mapFailure(Function<? super Failure, ? extends Failure> mapper) {
    return ImmutableValidated.of(validation().mapError(f -> f.mapFailure(mapper)));
  }

  default Validated<T> withFailuresOf(Function<? super T, ? extends Failures> failures) {
    return withFailures(validation().fold(identity(), failures));
  }

  default Validated<T> withFailuresOf(Fallible fallible) {
    return withFailures(fallible.failures());
  }

  default Validated<T> withFailures(Failures failures) {
    return failures.isEmpty() ? this
        : ImmutableValidated.of(Validation.invalid(validation().fold(failures::add, i -> failures)));
  }

  default boolean isValid() {
    return validation().isValid();
  }

  default boolean isInvalid() {
    return validation().isInvalid();
  }

  default Failures failures() {
    return validation().fold(identity(), i -> Failures.empty());
  }

  default Optional<T> get() {
    return validation().toJavaOptional();
  }

  default T mandatoryGet() {
    return validation().getOrElseThrow(() -> new IllegalStateException("invalid value"));
  }

  default Stream<T> toStream() {
    return Optionals.stream(get());
  }

  default T orElse(T defaultValue) {
    return get().orElse(defaultValue);
  }

  default T orElseThrow(Function<? super Failures, ? extends RuntimeException> error) {
    return validation().toEither().getOrElseThrow(error);
  }

  default T orElseThrow(Supplier<? extends RuntimeException> error) {
    return validation().getOrElseThrow(error);
  }

  default T orElseThrow() {
    return orElseThrow(Failures::toException);
  }
}
