package net.sfr.tv.validation;

import static java.util.function.Function.identity;
import static net.sfr.tv.mixin.functions.Functions.functionize;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.sfr.tv.mixin.Optionals;

public abstract class Unsaved<T> {

  final T value;
  final Consumer<T> saver;

  protected Unsaved(T value, Consumer<T> saver) {
    this.value = value;
    this.saver = saver;
  }

  public static <T> UnsavedLead<T> from(T value, Consumer<T> saver) {
    return new UnsavedLead<>(value, saver);
  }

  public static <U, P extends Unsaved<U>, T> UnsavedFollower<U, P, T> empty(P parent) {
    return new UnsavedFollower<>(parent, null, new EmptySaver<>());
  }

  public static <T> UnsavedLead<T> empty() {
    return new UnsavedLead<>(null, new EmptySaver<>());
  }

  static class EmptySaver<T> implements Consumer<T> {

    @Override
    public void accept(T t) {
      // empty as the name implies
    }
  }

  Consumer<T> mergeSaver(Consumer<T> saver) {
    return Optional.of(this.saver).filter(s -> !(s instanceof EmptySaver)).orElse(saver);
  }

  public boolean exists(Predicate<T> condition) {
    return condition.test(value);
  }

  public Optional<T> save() {
    return Optional.ofNullable(value).map(functionize(saver));
  }

  public T mandatorySave() {
    return save().orElseThrow(() -> new IllegalStateException("empty value"));
  }

  public static class UnsavedFollower<U, P extends Unsaved<U>, T> extends Unsaved<T> {

    private final P parent;

    public UnsavedFollower(P parent, T value, Consumer<T> saver) {
      super(value, saver);
      this.parent = parent;
    }

    public <E> UnsavedFollower<T, UnsavedFollower<U, P, T>, E> map(Function<T, E> mapper,
        Consumer<E> saver) {
      return new UnsavedFollower<>(this, mapper.apply(value), saver);
    }

    public <E> UnsavedFollower<T, UnsavedFollower<U, P, T>, E> map(
        Function<T, UnsavedLead<E>> mapper) {
      UnsavedLead<E> unsavedLead = mapper.apply(value);
      return new UnsavedFollower<>(this, unsavedLead.value, unsavedLead.saver);
    }

    public <E> Validated<UnsavedFollower<T, UnsavedFollower<U, P, T>, E>> flatMapValidated(
        Function<T, Validated<UnsavedLead<E>>> mapper) {
      return mapper.apply(value).validation()
          .fold(Is::invalid, v -> Is.valid(new UnsavedFollower<>(this, v.value, v.saver)));
    }

    public Merger<U, P, T> merge(UnsavedFollower<U, P, T> unsavedFollower,
        BinaryOperator<T> merger) {
      return new Merger<>(this, unsavedFollower, merger);
    }

    public static class Merger<U, P extends Unsaved<U>, T> {

      private final UnsavedFollower<U, P, T> v1;
      private final UnsavedFollower<U, P, T> v2;
      private final BinaryOperator<T> merger;

      public Merger(UnsavedFollower<U, P, T> v1,
          UnsavedFollower<U, P, T> v2, BinaryOperator<T> merger) {
        this.v1 = v1;
        this.v2 = v2;
        this.merger = merger;
      }

      public UnsavedFollower<U, P, T> merge(BinaryOperator<P> parentMerger) {
        return v1.merge(v2, parentMerger, merger);
      }

    }

    public UnsavedFollower<U, P, T> merge(UnsavedFollower<U, P, T> unsavedFollower,
        BinaryOperator<P> parentMerger,
        BinaryOperator<T> merger) {
      return new UnsavedFollower<>(parentMerger.apply(parent, unsavedFollower.parent),
          Optionals.join(Optional.ofNullable(value), Optional.ofNullable(unsavedFollower.value))
              .combine(identity(), merger, identity()),
          mergeSaver(unsavedFollower.saver));
    }

    public Tuple2<U, Optional<T>> saveAll() {
      return Tuple.of(parent.mandatorySave(), super.save());
    }
  }

  public static class UnsavedLead<T> extends Unsaved<T> {

    public UnsavedLead(T value, Consumer<T> saver) {
      super(value, saver);
    }

    public <E> UnsavedFollower<T, UnsavedLead<T>, E> map(Function<T, E> mapper,
        Consumer<E> saver) {
      return new UnsavedFollower<>(this, mapper.apply(value), saver);
    }

    public <E> UnsavedFollower<T, UnsavedLead<T>, E> map(Function<T, UnsavedLead<E>> mapper) {
      UnsavedLead<E> unsavedLead = mapper.apply(value);
      return new UnsavedFollower<>(this, unsavedLead.value, unsavedLead.saver);
    }

    public <E> UnsavedLead<E> map(BiFunction<T, Consumer<T>, UnsavedLead<E>> mapper) {
      return mapper.apply(value, saver);
    }

    public <E extends Iterable<T>> UnsavedLead<E> lift(Function<T, E> lift) {
      return map((val, saver) -> new UnsavedLead<>(lift.apply(val), vals -> vals.forEach(saver)));
    }

    public <E> Validated<UnsavedFollower<T, UnsavedLead<T>, E>> flatMapValidated(
        Function<T, Validated<UnsavedLead<E>>> mapper) {
      return mapper.apply(value).validation()
          .fold(Is::invalid, v -> Is.valid(new UnsavedFollower<>(this, v.value, v.saver)));
    }

    public UnsavedLead<T> merge(UnsavedLead<T> unsavedLead, BinaryOperator<T> merger) {
      return new UnsavedLead<>(merger.apply(value, unsavedLead.value),
          mergeSaver(unsavedLead.saver));
    }
  }

}
