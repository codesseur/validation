package net.sfr.tv.validation;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import io.vavr.Tuple2;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public interface ToBe {

  static <T> Supplier<Validated<T>> invalid(String code) {
    return invalid(code, emptyList());
  }

  @SafeVarargs
  static <T> Supplier<Validated<T>> invalid(String code,
      Supplier<Tuple2<String, Object>>... context) {
    return () -> Is.invalid(code, Arrays.stream(context).map(Supplier::get).collect(toList()));
  }

  static <T> Supplier<Validated<T>> invalid(String code, List<Tuple2<String, Object>> context) {
    return () -> Is.invalid(code, context);
  }

  @SafeVarargs
  static <T> Supplier<Validated<T>> invalid(String code, Tuple2<String, Object>... context) {
    return () -> Is.invalid(code, context);
  }

  static <T> Supplier<Validated<T>> invalid(Failure... failures) {
    return () -> Is.invalid(failures);
  }

  static <T> Supplier<Validated<T>> invalid(Failures failures) {
    return () -> Is.invalid(failures);
  }

  static <T> Supplier<Validated<T>> valid(T value) {
    return () -> Is.valid(value);
  }
}
