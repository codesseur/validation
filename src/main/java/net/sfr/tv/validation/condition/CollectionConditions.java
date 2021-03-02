package net.sfr.tv.validation.condition;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static net.sfr.tv.validation.condition.ObjectConditions.fromNonNull;
import static net.sfr.tv.validation.condition.ObjectConditions.fromPredicateNonNull;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.sfr.tv.mixin.iterate.container.CollectionContainer;
import net.sfr.tv.validation.Context;
import net.sfr.tv.validation.Status;

public interface CollectionConditions {
  
  @SafeVarargs
  static <E, T extends Collection<E>> Function<T, Status> Size(int size, Tuple2<String, Object>... context) {
    return fromPredicateNonNull(actual -> actual.size() == size, context, Tuple.of("_expectedSize", size));
  }

  @SafeVarargs
  static <E, T extends Collection<E>> Function<T, Status> NotEmpty(Tuple2<String, Object>... context) {
    return fromPredicateNonNull(actual -> !actual.isEmpty(), context);
  }

  @SafeVarargs
  static <E, T extends Collection<E>> Function<T, Status> Distinct(Tuple2<String, Object>... context) {
    return Distinct(Function.identity(), context);
  }

  @SafeVarargs
  static <E, T extends Collection<E>> Function<T, Status> Distinct(Function<E, ?> extractor, Tuple2<String, Object>... context) {
    return fromNonNull(
        es -> {
          List<?> duplicates = es.stream()
              .collect(groupingBy(extractor))
              .entrySet().stream()
              .flatMap(e -> Stream.of(e).filter(i -> i.getValue().size() > 1))
              .map(Entry::getKey)
              .collect(toList());
          return duplicates.isEmpty() ? Status.ok() : Status.ko(Context.from("_duplicates", duplicates));
        }, context);
  }

  @SafeVarargs
  static <E, T extends Collection<E>> Function<T, Status> DuplicateMatch(Function<E, ?> extractor, Predicate<List<E>> duplicateCondition, Tuple2<String, Object>... context) {
    return fromPredicateNonNull(
        es -> es.stream().collect(groupingBy(extractor)).values().stream().allMatch(duplicateCondition), context);
  }

  @SafeVarargs
  static <E, T extends Collection<E>> Function<T, Status> Empty(Tuple2<String, Object>... context) {
    return fromPredicateNonNull(Collection::isEmpty, context);
  }

  @SafeVarargs
  static <E, T extends Collection<E>> Function<T, Status> NoneMatch(Predicate<E> matcher, Tuple2<String, Object>... context) {
    return fromPredicateNonNull(c -> c.stream().noneMatch(matcher), context);
  }

  @SafeVarargs
  static <E, T extends Collection<E>> Function<T, Status> AllMatch(Predicate<E> matcher, Tuple2<String, Object>... context) {
    return fromPredicateNonNull(c -> c.stream().allMatch(matcher), context);
  }

  @SafeVarargs
  static <E, T extends Collection<E>> Function<T, Status> AnyMatch(Predicate<E> matcher, Tuple2<String, Object>... context) {
    return fromPredicateNonNull(c -> c.stream().anyMatch(matcher), context);
  }

  @SafeVarargs
  static <E, T extends Collection<E>> Function<T, Status> Contains(E element, Tuple2<String, Object>... context) {
    return fromPredicateNonNull(c -> c.contains(element), context);
  }

  @SafeVarargs
  static <E, T extends Collection<E>> Function<T, Status> NotContains(E element, Tuple2<String, Object>... context) {
    return fromPredicateNonNull(c -> !c.contains(element), context);
  }

  @SafeVarargs
  static <E, T extends Collection<E>, B extends CollectionContainer<E, T>> Function<T, Status> SubsetOf(B container, Tuple2<String, Object>... context) {
    return SubsetOf(container.value(), context);
  }

  @SafeVarargs
  static <E, T extends Collection<E>> Function<T, Status> SubsetOf(Collection<E> container, Tuple2<String, Object>... context) {
    return fromPredicateNonNull(container::containsAll, context, Tuple.of("_elements", container));
  }

}
