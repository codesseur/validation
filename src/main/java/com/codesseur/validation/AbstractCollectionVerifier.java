package com.codesseur.validation;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import com.codesseur.iterate.Streamed;
import io.vavr.Tuple;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractCollectionVerifier<E, C extends Collection<E>, S extends AbstractCollectionVerifier<E, C, S>> extends
    Verifier<C, S> {

  public S isEmpty() {
    return isNotNull().then().satisfies(Collection::isEmpty, () -> Failures.of("NOT_EMPTY"));
  }

  public S isNotEmpty() {
    return isNotNull().then().violates(Collection::isEmpty, () -> Failures.of("EMPTY"));
  }

  public S hasSize(int size) {
    return isNotNull().then().satisfies(
        v -> v.size() == size, () -> Failures.of("EMPTY", Tuple.of("size", size)));
  }

  public S isDistinct() {
    return isDistinct(Function.identity());
  }

  public S isDistinct(Function<? super E, ?> extractor) {
    return isNotNull().then().satisfies(v -> {
      List<E> duplicates = new ArrayList<>();
      long count = Streamed.of(v).distinctBy(extractor, (v1, v2) -> {
        duplicates.add(v2);
        return v1;
      }).count();
      return v.size() == count ? right(v) : left(Failures.of("EMPTY", Tuple.of("duplicates", duplicates)));
    });
  }

  public S nonMatch(Predicate<? super E> matcher) {
    return isNotNull().then().satisfies(c -> c.stream().noneMatch(matcher), () -> Failures.of("MATCH"));
  }

  public S allMatch(Predicate<? super E> matcher) {
    return isNotNull().then().satisfies(c -> c.stream().allMatch(matcher), () -> Failures.of("NOT_ALL_MATCH"));
  }

  public S anyMatch(Predicate<? super E> matcher) {
    return isNotNull().then().satisfies(c -> c.stream().anyMatch(matcher), () -> Failures.of("NONE_MATCH"));
  }

  public S contains(E element) {
    return isNotNull().then()
        .satisfies(c -> c.contains(element), () -> Failures.of("NOT_CONTAINS", Tuple.of("element", element)));
  }

  public S notContains(E element) {
    return isNotNull().then()
        .satisfies(c -> !c.contains(element), () -> Failures.of("CONTAINS", Tuple.of("element", element)));
  }

  public S isSubsetOf(Iterable<? extends E> elements) {
    Set<E> set = Streamed.<E>of(elements).toSet();
    return isNotNull().then()
        .satisfies(set::containsAll, () -> Failures.of("NOT_SUBSET", Tuple.of("elements", elements)));
  }

}
