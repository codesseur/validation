package net.sfr.tv.validation;

import static java.util.Collections.emptyMap;

import io.vavr.Tuple2;
import java.util.Optional;
import net.sfr.tv.mixin.Optionals;
import net.sfr.tv.mixin.iterate.container.Dictionary;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Context implements Dictionary<String, Object> {

  public static Context empty() {
    return ImmutableContext.builder().putAllValue(emptyMap()).build();
  }

  public static Context from(String name, Object value) {
    return ImmutableContext.builder().putValue(name, value).build();
  }

  public static Context from(Tuple2<String, Object>... context) {
    return ImmutableContext.of(Dictionary.of(context).value());
  }

  public Context add(Context context) {
    return ImmutableContext.builder().from(this)
        .putAllValue(context.value()).build();
  }

  public Context add(String key, Object value) {
    return Optionals.and(Optional.ofNullable(key), Optional.ofNullable(value))
        .map(t -> ImmutableContext.builder().from(this)
            .putValue(key, value)
            .build())
        .orElse(this);
  }

}
