package net.sfr.tv.validation.condition;

import io.vavr.Tuple2;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.sfr.tv.validation.Status;

public interface OptionalConditions{

  @SafeVarargs
  static <T> Function<Optional<T>, Status> IfPresent(Predicate<T> condition, Tuple2<String, Object>... context) {
    return ObjectConditions.fromPredicate(actual -> actual.map(condition::test).orElse(true), context);
  }

}
