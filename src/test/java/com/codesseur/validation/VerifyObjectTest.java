package com.codesseur.validation;

import com.codesseur.iterate.container.Sequence;
import com.codesseur.reflect.Type.$;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyObjectTest {

  @Test
  public void isEqualOnNoneEquals() {
    Assertions.assertThatThrownBy(
        () -> Verify.that("test").isEqualTo("bla").otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void isEqualOnEquals() {
    Verify.that("test").isEqualTo("test").otherwiseThrow();
  }

  @Test
  public void isNotEqualOnNoneEquals() {
    Verify.that("test").isNotEqualTo("bla").otherwiseThrow();
  }

  @Test
  public void isNotEqualOnEquals() {
    Assertions.assertThatThrownBy(
        () -> Verify.that("test").isNotEqualTo("test").otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void nullValidationWithNonNullObject() {
    Assertions.assertThatThrownBy(
        () -> Verify.that(new ArrayList<>()).isNull().otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void nullValidationWithNullObject() {
    Verify.that((Object) null).isNull().otherwiseThrow();
  }

  @Test
  public void nonNullValidationWithNullObject() {
    Assertions.assertThatThrownBy(
        () -> Verify.that((Object) null).isNotNull().otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void nonNullValidationWithNonNullObject() {
    Verify.that(new ArrayList<>()).isNotNull().otherwiseThrow();
  }

  @Test
  public void inWithMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Verify.that("two").isIn(duplicate).otherwiseThrow();
  }

  @Test
  public void inWithNoMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that("three").isIn(duplicate).otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void notInArrayWithMatchingElement() {
    Verify.that("one").notIn("zero", "two").otherwiseThrow();
  }

  @Test
  public void notInWithArrayNoMatchingElement() {
    Assertions.assertThatThrownBy(() -> Verify.that("three").notIn("three", "four").otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void inArrayWithMatchingElement() {
    Verify.that("one").isIn("one", "two").otherwiseThrow();
  }

  @Test
  public void innWithArrayNoMatchingElement() {
    Assertions.assertThatThrownBy(() -> Verify.that("three").isIn("two", "four").otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void notInWithMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Verify.that("three").notIn(duplicate).otherwiseThrow();
  }

  @Test
  public void notInWithNoMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that("one").notIn(duplicate).otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void notInOnSequenceWithMatchingElement() {
    Sequence<String> duplicate = () -> Arrays.asList("one", "two");

    Verify.that("three").notIn(duplicate).otherwiseThrow();
  }

  @Test
  public void notInOnSequenceWithNoMatchingElement() {
    Sequence<String> duplicate = () -> Arrays.asList("one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that("one").notIn(duplicate).otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void map() {
    Verify.that("one").isNotEmpty().mapAs(String::length, A::comparable).isGreaterThan(2).otherwiseThrow();
  }

  @Test
  public void otherwiseWithCode() {
    Assertions.assertThatThrownBy(() -> Verify.that("one").notIn("one")
        .otherwise("Bla")
        .otherwiseThrow())
        .isInstanceOfSatisfying(ValidationException.class, v -> {
          Assertions.assertThat(v.details().size()).isEqualTo(1);
          Assertions.assertThat(v.details().head().map(Failure::code)).hasValue("Bla");
        });
  }

  @Test
  public void otherwiseThrowLast() {
    Assertions.assertThatThrownBy(() -> Verify.that("one")
        .notIn("one").otherwise("Bla")
        .isEmpty().otherwise("Blou")
        .otherwiseThrowLast(f -> new RuntimeException(f.code())))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Blou");
  }

  @Test
  public void instanceOf() {
    Assertions.assertThatThrownBy(() -> Verify.that((Object) "one")
        .isInstanceOf($.$(), A::string)
        .isEmpty().otherwise("Blou")
        .otherwiseThrowLast(f -> new RuntimeException(f.code())))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Blou");
  }

}