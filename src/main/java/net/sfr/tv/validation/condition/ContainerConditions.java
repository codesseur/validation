package net.sfr.tv.validation.condition;

import static net.sfr.tv.validation.condition.ObjectConditions.fromNonNull;

import io.vavr.Tuple2;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import net.sfr.tv.mixin.iterate.container.CollectionContainer;
import net.sfr.tv.validation.Status;

public interface ContainerConditions {
  
  @SafeVarargs
  static <E, C extends Collection<E>, T extends CollectionContainer<E,C>> Function<T, Status> Size(int size, Tuple2<String, Object>... context) {
    return from(CollectionConditions.Size(size, context));
  }

  @SafeVarargs
  static <E, C extends Collection<E>, T extends CollectionContainer<E,C>> Function<T, Status> NotEmpty(Tuple2<String, Object>... context) {
    return from(CollectionConditions.NotEmpty(context));
  }

  @SafeVarargs
  static <E, C extends Collection<E>, T extends CollectionContainer<E,C>> Function<T, Status> Distinct(Tuple2<String, Object>... context) {
    return Distinct(Function.identity(), context);
  }

  @SafeVarargs
  static <E, C extends Collection<E>, T extends CollectionContainer<E,C>> Function<T, Status> Distinct(Function<E, ?> extractor, Tuple2<String, Object>... context) {
    return from(CollectionConditions.Distinct(extractor, context));
  }

  @SafeVarargs
  static <E, C extends Collection<E>, T extends CollectionContainer<E,C>> Function<T, Status> DuplicateMatch(Function<E, ?> extractor, Predicate<List<E>> duplicateCondition, Tuple2<String, Object>... context) {
    return from(CollectionConditions.DuplicateMatch(extractor, duplicateCondition, context));  }

  @SafeVarargs
  static <E, C extends Collection<E>, T extends CollectionContainer<E,C>> Function<T, Status> Empty(Tuple2<String, Object>... context) {
    return from(CollectionConditions.Empty(context));  }

  @SafeVarargs
  static <E, C extends Collection<E>, T extends CollectionContainer<E,C>> Function<T, Status> NoneMatch(Predicate<E> matcher, Tuple2<String, Object>... context) {
    return from(CollectionConditions.NoneMatch(matcher, context));  }

  @SafeVarargs
  static <E, C extends Collection<E>, T extends CollectionContainer<E,C>> Function<T, Status> AllMatch(Predicate<E> matcher, Tuple2<String, Object>... context) {
    return from(CollectionConditions.AllMatch(matcher, context));  }

  @SafeVarargs
  static <E, C extends Collection<E>, T extends CollectionContainer<E,C>> Function<T, Status> AnyMatch(Predicate<E> matcher, Tuple2<String, Object>... context) {
    return from(CollectionConditions.AnyMatch(matcher, context));  }

  @SafeVarargs
  static <E, C extends Collection<E>, T extends CollectionContainer<E,C>> Function<T, Status> Contains(E element, Tuple2<String, Object>... context) {
    return from(CollectionConditions.Contains(element, context));  }

  @SafeVarargs
  static <E, C extends Collection<E>, T extends CollectionContainer<E,C>> Function<T, Status> NotContains(E element, Tuple2<String, Object>... context) {
    return from(CollectionConditions.NotContains(element, context));  }

  @SafeVarargs
  static <E, C extends Collection<E>, T extends CollectionContainer<E, C>> Function<T, Status> SubsetOf(T container, Tuple2<String, Object>... context) {
    return SubsetOf(container.value(), context);
  }

  @SafeVarargs
  static <E, C extends Collection<E>, T extends CollectionContainer<E,C>> Function<T, Status> SubsetOf(Collection<E> container, Tuple2<String, Object>... context) {
    return from(CollectionConditions.SubsetOf(container, context));  }

  @SafeVarargs
  private static <E, C extends Collection<E>, T extends CollectionContainer<E,C>> Function<T, Status> from(Function<C, Status> condition, Tuple2<String, Object>... context) {
    return fromNonNull(actual -> condition.apply(actual.value()),context);
  }

}
