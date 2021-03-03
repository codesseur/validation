package net.sfr.tv.validation.hard;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.sfr.tv.mixin.iterate.container.CollectionContainer;
import net.sfr.tv.validation.Context;

public abstract class GenericCollectionVerifier<E, T extends Collection<E>, S extends GenericCollectionVerifier<E, T, S>> extends
    GenericVerifier<T, S> {

  public OrElse<T> hasSize(int size) {
    return addContext("size", size)
        .satisfiesWithOptional(op -> op.filter(e -> e.size() == size).isPresent());
  }

  public OrElse<T> isNotEmpty() {
    return satisfies(es -> !es.isEmpty());
  }

  public OrElse<T> isDistinct() {
    return isDistinct(Function.identity());
  }

  public OrElse<T> isDistinct(Function<E, ?> extractor) {
    return satisfiesWithStatus(
        es -> {
          List<?> duplicates = es.stream()
              .collect(groupingBy(extractor))
              .entrySet().stream()
              .flatMap(e -> Stream.of(e).filter(i -> i.getValue().size() > 1))
              .map(Entry::getKey)
              .collect(toList());
          return duplicates.isEmpty() ? Status.ok() : Status.ko(Context.from("duplicates", duplicates));
        });
  }

  public OrElse<T> duplicateMatch(Function<E, ?> extractor, Predicate<List<E>> duplicateCondition) {
    return satisfies(
        es -> es.stream().collect(groupingBy(extractor)).values().stream()
            .allMatch(duplicateCondition));
  }

  public OrElse<T> isEmpty() {
    return satisfies(Collection::isEmpty);
  }

  public OrElse<T> nonMatch(Predicate<E> matcher) {
    return satisfies(c -> c.stream().noneMatch(matcher));
  }

  public OrElse<T> allMatch(Predicate<E> matcher) {
    return satisfies(c -> c.stream().allMatch(matcher));
  }

  public OrElse<T> anyMatch(Predicate<E> matcher) {
    return satisfies(c -> c.stream().anyMatch(matcher));
  }

  public OrElse<T> contains(E element) {
    return satisfies(c -> c.contains(element));
  }

  public OrElse<T> notContains(E element) {
    return satisfies(c -> !c.contains(element));
  }

  public <B extends CollectionContainer<E, T>> OrElse<T> isSubsetOf(B container) {
    return addContext("elements", container)
        .satisfiesWithOptional(
            o -> o.filter(t -> t.stream().allMatch(container::contains)).isPresent());
  }

}
