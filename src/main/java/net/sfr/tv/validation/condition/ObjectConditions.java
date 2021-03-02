package net.sfr.tv.validation.condition;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.sfr.tv.mixin.iterate.container.CollectionContainer;
import net.sfr.tv.validation.Status;

public interface ObjectConditions {

  @SafeVarargs
  static <T> Function<T, Status> EqualTo(T expected, Tuple2<String, Object>... context) {
    return fromPredicate(actual -> Objects.equals(actual, expected), context, Tuple.of("_expected", expected));
  }

  @SafeVarargs
  static <T> Function<T, Status> NotNull(Tuple2<String, Object>... context) {
    return fromPredicate(Objects::nonNull, context);
  }

  @SafeVarargs
  static <T> Function<T, Status> Null(Tuple2<String, Object>... context) {
    return fromPredicate(Objects::isNull, context);
  }

  @SafeVarargs
  static <T, C extends Collection<T>> Function<T, Status> In(C elements, Tuple2<String, Object>... context) {
    return fromPredicate(elements::contains, context);
  }

  @SafeVarargs
  static <T, C extends Collection<T>> Function<T, Status> NotIn(C elements, Tuple2<String, Object>... context) {
    return fromPredicate(o -> !elements.contains(o), context);
  }

  @SafeVarargs
  static <T, L extends Collection<T>, C extends CollectionContainer<T, L>> Function<T, Status> In(
      C elements, Tuple2<String, Object>... context) {
    return fromPredicate(elements::contains, context);
  }

  @SafeVarargs
  static <T, L extends Collection<T>, C extends CollectionContainer<T, L>> Function<T, Status> NotIn(
      C elements, Tuple2<String, Object>... context) {
    return fromPredicate(elements::notContains, context);
  }

  @SafeVarargs
  static <T> Function<T, Status> fromPredicate(Predicate<T> value, Tuple2<String, Object>[] first,
      Tuple2<String, Object>... context) {
    return fromGeneric(v -> Status.withoutContext(value.test(v)), first, context);
  }

  @SafeVarargs
  static <T> Function<T, Status> fromPredicateNonNull(Predicate<T> value, Tuple2<String, Object>[] first,
      Tuple2<String, Object>... context) {
    return fromNonNull(v -> Status.withContext(value.test(v)), first, context);
  }

  @SafeVarargs
  static <T> Function<T, Status> fromNonNull(Function<? super T, ? extends Status> condition,
      Tuple2<String, Object>[] first,
      Tuple2<String, Object>... context) {
    return fromGeneric(v -> Optional.ofNullable(v).<Status>map(condition).orElseGet(Status::ko), first, context);
  }

  @SafeVarargs
  static <T> Function<T, Status> fromGeneric(Function<? super T, ? extends Status> condition,
      Tuple2<String, Object>[] first,
      Tuple2<String, Object>... context) {
    return v -> condition.apply(v).addToContext(context).addToContext(first).addToContext("_actual", v);
  }
}
