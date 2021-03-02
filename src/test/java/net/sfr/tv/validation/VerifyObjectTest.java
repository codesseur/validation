package net.sfr.tv.validation;

import static net.sfr.tv.validation.ToBe.invalid;
import static net.sfr.tv.validation.condition.ObjectConditions.In;
import static net.sfr.tv.validation.condition.ObjectConditions.NotIn;
import static net.sfr.tv.validation.condition.ObjectConditions.NotNull;
import static net.sfr.tv.validation.condition.ObjectConditions.Null;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.sfr.tv.mixin.iterate.container.Sequence;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyObjectTest {

  @Test
  public void nullValidationWithNonNullObject() {
    Assertions.assertThatThrownBy(
        () -> Verify.that(new ArrayList<>()).is(Null(), invalid("INVALID")).orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void nullValidationWithNullObject() {
    Verify.that(null).is(Null(), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void nonNullValidationWithNullObject() {
    Assertions.assertThatThrownBy(
        () -> Verify.that(null).is(NotNull(), invalid("INVALID")).orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void nonNullValidationWithNonNullObject() {
    Verify.that(new ArrayList<>()).is(NotNull(), invalid("INVALID")).orElseThrow();
  }
  

  @Test
  public void inWithMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Verify.that("two").is(In(duplicate)
        , invalid("INVALID")).orElseThrow();
  }

  @Test
  public void inWithNoMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that("three").is(In(duplicate)
        , invalid("INVALID")).orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void inOnSequenceWithMatchingElement() {
    Sequence<String> duplicate = () -> Arrays.asList("one", "two");

    Verify.that("one").is(In(duplicate)
        , invalid("INVALID")).orElseThrow();
  }

  @Test
  public void inOnSequenceWithNoMatchingElement() {
    Sequence<String> duplicate = () -> Arrays.asList("one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that("three").is(In(duplicate)
        , invalid("INVALID")).orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void notInWithMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Verify.that("three").is(NotIn(duplicate), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void notInWithNoMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that("one").is(NotIn(duplicate), invalid("INVALID")).orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void notInOnSequenceWithMatchingElement() {
    Sequence<String> duplicate = () -> Arrays.asList("one", "two");

    Verify.that("three").is(NotIn(duplicate), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void notInOnSequenceWithNoMatchingElement() {
    Sequence<String> duplicate = () -> Arrays.asList("one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that("one").is(NotIn(duplicate)
        , invalid("INVALID")).orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

}