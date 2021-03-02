package net.sfr.tv.validation.condition;

import static net.sfr.tv.validation.condition.ObjectConditions.fromPredicate;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.function.Function;
import net.sfr.tv.validation.Status;
import org.immutables.value.Value;

@Value.Immutable
public interface BooleanConditions {

  @SafeVarargs
  static Function<Boolean, Status> True(Tuple2<String, Object>... context) {
    return fromPredicate(v -> v, context, Tuple.of("_expected", true));
  }

  @SafeVarargs
  static Function<Boolean, Status> False(Tuple2<String, Object>... context) {
    return fromPredicate(v -> !v, context,Tuple.of("_expected", false));
  }
}
