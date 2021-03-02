package net.sfr.tv.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class View<T> {

  private final Validated<T> validated;
  private final List<Function<Validated<T>, Validated<T>>> mappers = new ArrayList<>();

  public View(Validated<T> validated) {
    this.validated = validated;
  }

  public View<T> is(Predicate<? super T> condition, Supplier<? extends Validated<T>> orElse) {
    return is(condition, i -> orElse.get());
  }

  public View<T> is(Predicate<? super T> condition, Function<? super T, ? extends Validated<T>> orElse) {
    mappers.add(v -> v.filterPredicate(condition, orElse));
    return this;
  }

  public View<T> satisfies(Function<? super T, ? extends Status> condition, Supplier<? extends Validated<T>> orElse) {
    return satisfies(condition, i -> orElse.get());
  }

  public View<T> satisfies(Function<? super T, ? extends Status> condition, Function<? super T, ? extends Validated<T>> orElse) {
    mappers.add(v -> v.filter(condition, orElse));
    return this;
  }

  public Validated<T> then() {
    return force();
  }

  public Validated<T> force() {
    return mappers.stream()
        .map(f -> f.apply(validated))
        .reduce(validated, (v1, v2) -> v1.merge(v2, (t1, t2) -> t2));
  }

  public T orElseThrow(Function<? super Failures, ? extends RuntimeException> error) {
    return force().orElseThrow(error);
  }

  public T orElseThrow(Supplier<? extends RuntimeException> error) {
    return force().orElseThrow(error);
  }

  public T orElseThrow() {
    return force().orElseThrow();
  }
}
