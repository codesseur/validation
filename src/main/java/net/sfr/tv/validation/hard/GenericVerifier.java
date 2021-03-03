package net.sfr.tv.validation.hard;

import io.vavr.control.Validation;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.sfr.tv.mixin.iterate.container.CollectionContainer;
import net.sfr.tv.validation.Context;
import net.sfr.tv.validation.Failures;
import net.sfr.tv.validation.ImmutableContext;
import net.sfr.tv.validation.ImmutableContext.Builder;
import net.sfr.tv.validation.Validated;
import org.immutables.value.Value.Default;

public abstract class GenericVerifier<VALUE, SELF extends GenericVerifier<VALUE, SELF>> implements Validated<VALUE> {

  abstract Optional<VALUE> value();

  abstract Optional<String> label();

  abstract Optional<Status> result();

  @Override
  public Validation<Failures, VALUE> validation() {
    return Validation.valid(value().orElse(null));
  }

  @Default
  Context context() {
    return Context.empty();
  }

  public abstract SELF label(String name);

  public abstract SELF addContext(String key, Object value);

  protected Context initialContext() {
    Builder builder = ImmutableContext.builder().putValue("label", label().orElse("value"));
    value().ifPresent(v -> builder.putValue("self", v));
    return builder.build();
  }

  public OrElse<VALUE> isEqualTo(VALUE expected) {
    return addContext("expected", expected)
        .satisfiesWithOptional(o -> Objects.equals(o.orElse(null), expected));
  }

  public OrElse<VALUE> isNotNull() {
    return satisfiesWithOptional(Optional::isPresent);
  }

  public OrElse<VALUE> isNull() {
    return violatesWithOptional(Optional::isPresent);
  }

  public <L extends Collection<VALUE>> OrElse<VALUE> isIn(L elements) {
    return addContext("elements", elements)
        .satisfiesWithOptional(o -> o.filter(elements::contains).isPresent());
  }

  public <L extends Collection<VALUE>, C extends CollectionContainer<VALUE, L>> OrElse<VALUE> isIn(
      C elements) {
    return addContext("elements", elements.value())
        .satisfiesWithOptional(o -> o.filter(elements::contains).isPresent());
  }

  public <L extends Collection<VALUE>> OrElse<VALUE> notIn(L elements) {
    return addContext("elements", elements)
        .satisfiesWithOptional(o -> o.filter(elements::contains).isEmpty());
  }

  public <L extends Collection<VALUE>, C extends CollectionContainer<VALUE, L>> OrElse<VALUE> notIn(
      C elements) {
    return addContext("elements", elements.value())
        .satisfiesWithOptional(o -> o.filter(elements::contains).isEmpty());
  }

  public OrElse<VALUE> violates(Predicate<VALUE> condition) {
    return violatesWithOptional(op -> op.filter(condition).isPresent());
  }

  public OrElse<VALUE> violatesWithOptional(Predicate<Optional<VALUE>> condition) {
    return satisfiesWithOptional(condition.negate());
  }

  public OrElse<VALUE> satisfies(Predicate<VALUE> condition) {
    return satisfiesWithOptional(op -> op.filter(condition).isPresent());
  }

  public OrElse<VALUE> satisfiesWithStatus(Function<VALUE, Status> condition) {
    return satisfiesOptionalWithStatus(op -> op.map(condition).orElseGet(Status::ko));
  }

  public OrElse<VALUE> satisfiesWithOptional(Predicate<Optional<VALUE>> condition) {
    return satisfiesOptionalWithStatus(
        v -> Status.withoutContext(condition.test(v)));
  }

  public OrElse<VALUE> satisfiesOptionalWithStatus(Function<Optional<VALUE>, Status> condition) {
    Status result = result().map(r -> r.ifOk(c -> check(condition)))
        .orElseGet(() -> check(condition));
    return ImmutableOrElse.of(value(), result);
  }

  private Status check(Function<Optional<VALUE>, Status> condition) {
    Status result = condition.apply(value());
    return result.context(initialContext().add(context()).add(result.context()));
  }

}
