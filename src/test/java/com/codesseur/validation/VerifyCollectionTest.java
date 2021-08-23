package com.codesseur.validation;

import com.codesseur.validation.Verify;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyCollectionTest {

  private final List<String> one = Collections.singletonList("one");
  private final List<String> duplicate = Arrays.asList("one", "one");
  private final List<String> duplicates = Arrays.asList("one", "one", "two", "two");
  private final List<String> empty = Collections.emptyList();

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
    List<String> duplicate = Arrays.asList("one", "two");

    Verify.that(duplicate).contains("one").otherwiseThrow();
  }

  @Test
  public void containsWithNoMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that(duplicate).contains("three")
        .otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void notContainsWithMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Verify.that(duplicate).notContains("three").otherwiseThrow();
  }

  @Test
  public void notContainsWithNoMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that(duplicate).notContains("one")
        .otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void isSubsetOfWithSubset() {
    List<String> set = Arrays.asList("one", "two", "three");

    Verify.that(one).isSubsetOf(set).otherwiseThrow();
  }

  @Test
  public void isSubsetOfWithNoSubset() {
    List<String> set = Arrays.asList("two", "three");

    Assertions.assertThatThrownBy(() -> Verify.that(one).isSubsetOf(set)
        .otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

}