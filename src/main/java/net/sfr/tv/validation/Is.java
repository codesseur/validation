package net.sfr.tv.validation;

import io.vavr.Tuple2;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class Is {
  public static <T> Validated<T> narrow(Optional<T> value, Supplier<Validated<T>> orElse) {
    return value.map(Is::valid).orElseGet(orElse);
  }

  public static <E, T> Validated<T> narrow(Either<E, T> value, Function<E, Validated<T>> orElse) {
    return value.map(Is::valid).getOrElseGet(orElse);
  }

  @SafeVarargs
  public static <T> Validated<T> invalid(String code, Tuple2<String, Object>... context) {
    return invalid(Failures.from(code, context));
  }

  public static <T> Validated<T> invalid(String code, List<Tuple2<String, Object>> context) {
    return invalid(Failures.from(code, context));
  }

  public static <T> Validated<T> invalid(Failure... failures) {
    return invalid(Failures.from(failures));
  }

  public static <T> Validated<T> invalid(Failures failures) {
    return ImmutableValidated.of(Validation.invalid(failures));
  }

  public static <T> Validated<T> valid(T value) {
    return ImmutableValidated.of(Validation.valid(value));
  }

}
