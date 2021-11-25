package com.codesseur.validation;

import com.codesseur.iterate.container.Sequence;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyContainerTest {

  private final Sequence<String> one = Sequence.of("one");
  private final Sequence<String> duplicate = Sequence.of("one", "one");
  private final Sequence<String> duplicates = Sequence.of("one", "one", "two", "two");
  private final Sequence<String> empty = Sequence.empty();

  @Test
  public void nonEmptyValidationWithEmptyCollection() {
    Assertions.assertThatThrownBy(() -> Verify.that(empty).isNotEmpty().otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void nonEmptyValidationWithNonEmptyCollection() {
    Verify.that(one).isNotEmpty().otherwiseThrow();
  }

  @Test
  public void emptyValidationWithNonEmptyCollection() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).isEmpty()
        .otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void emptyValidationWithEmptyCollection() {
    Verify.that(empty).isEmpty().otherwiseThrow();
  }

  @Test
  public void anyMatchValidationWithMatchingCollection() {
    Verify.that(one).anyMatch(s -> s.startsWith("o")).otherwiseThrow();
  }

  @Test
  public void allMatchValidationWithMatchingCollection() {
    Verify.that(one).allMatch(s -> s.startsWith("o")).otherwiseThrow();
  }

  @Test
  public void noneMatchValidationWithMatchingCollection() {
    Verify.that(one).nonMatch(s -> s.startsWith("b")).otherwiseThrow();
  }

  @Test
  public void hasSizeValidationWithMatchingCollection() {
    Verify.that(one).hasSize(1).otherwiseThrow();
  }

  @Test
  public void isDistinctWithDistinctElements() {
    Verify.that(one).isDistinct().otherwiseThrow();
  }

  @Test
  public void isDistinctWithDuplicateElements() {
    Assertions.assertThatThrownBy(() -> Verify.that(duplicate).isDistinct()
        .otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void isDistinctWithDuplicatesElements() {
    Assertions.assertThatThrownBy(() -> Verify.that(duplicates).isDistinct()
        .otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void containsWithMatchingElement() {
    Sequence<String> duplicate = Sequence.of("one", "two");

    Verify.that(duplicate).contains("one").otherwiseThrow();
  }

  @Test
  public void containsWithNoMatchingElement() {
    Sequence<String> duplicate = Sequence.of("one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that(duplicate).contains("three")
        .otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void notContainsWithMatchingElement() {
    Sequence<String> duplicate = Sequence.of("one", "two");

    Verify.that(duplicate).notContains("three").otherwiseThrow();
  }

  @Test
  public void notContainsWithNoMatchingElement() {
    Sequence<String> duplicate = Sequence.of("one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that(duplicate).notContains("one")
        .otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void isSubsetOfWithSubset() {
    Sequence<String> set = Sequence.of("one", "two", "three");

    Verify.that(one).isSubsetOf(set).otherwiseThrow();
  }

  @Test
  public void isSubsetOfWithNoSubset() {
    Sequence<String> set = Sequence.of("two", "three");

    Assertions.assertThatThrownBy(() -> Verify.that(one).isSubsetOf(set)
        .otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

}