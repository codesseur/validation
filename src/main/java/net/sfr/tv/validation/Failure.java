package net.sfr.tv.validation;

import io.vavr.Tuple2;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import net.sfr.tv.mixin.iterate.container.Dictionary;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Failure {

  public abstract FailureCode code();

  public abstract Context context();

  @SafeVarargs
  public static Failure from(String code, Tuple2<String, Object>... context) {
    return ImmutableFailure.builder().code(code).context(Dictionary.of(context).value()).build();
  }

  public static Failure from(String code, List<Tuple2<String, Object>> context) {
    return ImmutableFailure.builder().code(code)
        .context(Dictionary.of(context.stream()).value())
        .build();
  }

  public static Failure from(String code, Context context) {
    return ImmutableFailure.builder().code(code)
        .context(context)
        .build();
  }

  public Failure addToContext(String key, Object value) {
    return Optional.ofNullable(value)
        .<Failure>map(
            v -> ImmutableFailure.builder().code(code()).context(context().add(key, v)).build())
        .orElse(this);
  }

  public Failure addToContext(Context context) {
    return ImmutableFailure.builder().from(this).context(context().add(context)).build();
  }

  public Failure prefixCode(String prefix) {
    return mapCode(prefix::concat);
  }

  public Failure suffixCode(String suffix) {
    return mapCode(c -> c + suffix);
  }

  public Failure mapCode(UnaryOperator<String> mapper) {
    return ImmutableFailure.builder().from(this).code(mapper.apply(code().message())).build();
  }
}