package com.codesseur.validation;

import com.codesseur.iterate.container.Dictionary;
import io.vavr.Tuple2;
import java.util.Collections;
import org.immutables.value.Value.Immutable;

@Immutable
public interface Context extends Dictionary<String, Object> {

  static Context empty() {
    return ImmutableContext.of(Collections.emptyMap());
  }

  @SafeVarargs
  static Context of(Tuple2<String, Object>... context) {
    return Dictionary.of(context).apply(ImmutableContext::of);
  }

  default Context merge(Context context) {
    return ImmutableContext.builder().from(this).putAllValue(context.value()).build();
  }
}
