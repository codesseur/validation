package net.sfr.tv.validation;

import io.vavr.Tuple2;
import io.vavr.collection.Vector;
import java.util.List;
import java.util.function.Function;
import net.sfr.tv.mixin.iterate.container.Bag;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Failures implements Bag<Failure> {

  public static Failures empty() {
    return ImmutableFailures.of(Vector.empty());
  }

  @SafeVarargs
  public static Failures from(String code, Tuple2<String, Object>... context) {
    return ImmutableFailures.builder().value(Vector.of(Failure.from(code, context))).build();
  }

  public static Failures from(String code, List<Tuple2<String, Object>> context) {
    return ImmutableFailures.builder().value(Vector.of(Failure.from(code, context))).build();
  }

  public static Failures from(Failure... failures) {
    return ImmutableFailures.builder().value(Vector.of((failures))).build();
  }

  public Failures add(Failures failures) {
    return ImmutableFailures.builder().from(this).addAllValue(failures.value()).build();
  }

  public Failures addToContext(String key, Object value) {
    return mapFailure(f -> f.addToContext(key, value));
  }

  public Failures addToContext(Context context) {
    return mapFailure(f -> f.addToContext(context));
  }

  public Failures mapFailure(Function<? super Failure, ? extends Failure> mapper) {
    return map(mapper).collect(ImmutableFailures::of);
  }

  public RuntimeException toException(Failure root) {
    return new ValidationException(root, this);
  }

  public RuntimeException toException() {
    return new ValidationException(this);
  }

  public boolean isEmpty() {
    return value().isEmpty();
  }
}