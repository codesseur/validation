package net.sfr.tv.validation;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import io.vavr.collection.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.sfr.tv.mixin.Optionals;

public class SoftBuilder<B> {

  private final List<Failure> failures = new ArrayList<>();
  private final Map<String, Object> context = new HashMap<>();
  private final B builder;

  public SoftBuilder(B builder) {
    this.builder = builder;
  }

  public <T> B map(Validated<T> validated, Function<T, B> mapper,
      Function<T, Map<String, Object>> unitContext) {
    Validated<T> validatedWithContext = validated.peek(v -> context.putAll(unitContext.apply(v)));
    return map(validatedWithContext, mapper);
  }

  public <T> B map(Validated<T> validated, Function<T, B> mapper) {
    return validated.map(mapper).peekError(this::add)
        .orElse(builder);
  }

  public <T> B map(Optional<Validated<T>> optional, Function<T, B> mapper) {
    return optional.map(validated -> validated.map(mapper).peekError(this::add).orElse(builder))
        .orElse(builder);
  }

  public <T> B map(Stream<Validated<T>> validated, Function<List<T>, B> mapper) {
    return validated.map(v -> v.peekError(this::add))
        .map(Validated::get)
        .flatMap(Optionals::stream)
        .collect(collectingAndThen(toList(), mapper));
  }

  public <T extends Checked> Validated<T> safeBuild(Supplier<? extends T> build) {
    if (failures.isEmpty()) {
      T entity = build.get();
      add(entity.check());

      if (failures.isEmpty()) {
        return Is.valid(entity);
      }
    }
    Failures finalFailures = ImmutableFailures.of(Vector.ofAll(this.failures))
        .addToContext(ImmutableContext.of(context));
    return Is.invalid(finalFailures);
  }

  private B add(Failures failures) {
    this.failures.addAll(failures.value());
    return builder;
  }

  public interface Checked {

    default Failures check() {
      return Failures.empty();
    }
  }

}
