package net.sfr.tv.validation;

import io.vavr.Tuple2;
import java.util.function.Function;
import net.sfr.tv.mixin.MicroType;
import org.immutables.value.Value;

@Value.Immutable
public interface Status extends MicroType<Boolean> {

  static Status withoutContext(boolean ok) {
    return ImmutableStatus.of(ok, Context.empty());
  }

  static Status withContext(boolean ok, Context context) {
    return ImmutableStatus.of(ok, context);
  }

  @SafeVarargs
  static Status withContext(boolean ok, Tuple2<String, Object>... context) {
    return ImmutableStatus.of(ok, Context.from(context));
  }

  static Status ok() {
    return withoutContext(true);
  }

  static Status ko() {
    return withoutContext(false);
  }

  static Status ko(Context context) {
    return ImmutableStatus.of(false, context);
  }

  Context context();

  default Status ifOk(Function<Context,Status> then) {
    return value() ? then.apply(context()) : this;
  }

  default Status context(Context context) {
    return ImmutableStatus.of(value(), context);
  }

  default Status addToContext(String key, Object value) {
    return ImmutableStatus.of(value(), context().add(key, value));
  }

  default Status addToContext(Tuple2<String,Object> entry) {
    return ImmutableStatus.of(value(), context().add(entry._1(), entry._2));
  }

  default Status addToContext(Tuple2<String,Object>...entry) {
    return ImmutableStatus.of(value(), context().add(Context.from(entry)));
  }

  default boolean isOk() {
    return value();
  }

  default boolean isKo() {
    return !isOk();
  }
}
