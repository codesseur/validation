package net.sfr.tv.validation;

import static net.sfr.tv.validation.ToBe.invalid;
import static net.sfr.tv.validation.condition.CollectionConditions.AllMatch;
import static net.sfr.tv.validation.condition.CollectionConditions.AnyMatch;
import static net.sfr.tv.validation.condition.CollectionConditions.Contains;
import static net.sfr.tv.validation.condition.CollectionConditions.Distinct;
import static net.sfr.tv.validation.condition.CollectionConditions.DuplicateMatch;
import static net.sfr.tv.validation.condition.CollectionConditions.Empty;
import static net.sfr.tv.validation.condition.CollectionConditions.NoneMatch;
import static net.sfr.tv.validation.condition.CollectionConditions.NotContains;
import static net.sfr.tv.validation.condition.CollectionConditions.NotEmpty;
import static net.sfr.tv.validation.condition.CollectionConditions.Size;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyCollectionTest {

  private List<String> one = Collections.singletonList("one");
  private List<String> duplicate = Arrays.asList("one", "one");
  private List<String> duplicates = Arrays.asList("one", "one", "two", "two");
  private List<String> empty = Collections.emptyList();

  @Test
  public void nonEmptyValidationWithEmptyCollection() {
    Assertions.assertThatThrownBy(() -> Verify.that(empty).is(NotEmpty(), invalid("INVALID")).orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void nonEmptyValidationWithNonEmptyCollection() {
    Verify.that(one).is(NotEmpty(), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void emptyValidationWithNonEmptyCollection() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).is(Empty(), invalid("INVALID"))
        .orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void emptyValidationWithEmptyCollection() {
    Verify.that(empty).is(Empty(), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void anyMatchValidationWithMatchingCollection() {
    Verify.that(one).is(AnyMatch(s -> s.startsWith("o")), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void allMatchValidationWithMatchingCollection() {
    Verify.that(one).is(AllMatch(s -> s.startsWith("o")), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void noneMatchValidationWithMatchingCollection() {
    Verify.that(one).is(NoneMatch(s -> s.startsWith("b")), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void hasSizeValidationWithMatchingCollection() {
    Verify.that(one).is(Size(1), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void isDistinctWithDistinctElements() {
    Verify.that(one).is(Distinct(), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void isDistinctWithDuplicateElements() {
    Assertions.assertThatThrownBy(() -> Verify.that(duplicate).is(Distinct(), invalid("INVALID"))
        .orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void isDistinctWithDuplicatesElements() {
    Assertions.assertThatThrownBy(() -> Verify.that(duplicates).is(Distinct(), invalid("INVALID"))
        .orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void duplicateMatchWithOneDuplicateElements() {
    List<String> duplicate = Arrays.asList("one", "one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that(duplicate).is(DuplicateMatch(
        Function.identity(), l -> l.size() == 1), invalid("INVALID"))
        .orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void duplicateMatchWithTwoDuplicateElements() {
    List<String> duplicate = Arrays.asList("one", "one", "two", "two");

    Verify.that(duplicate).is(DuplicateMatch(
        Function.identity(), l -> l.size() == 2), invalid("INVALID"))
        .orElseThrow();
  }

  @Test
  public void containsWithMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Verify.that(duplicate).is(Contains("one"), invalid("INVALID"))
        .orElseThrow();
  }

  @Test
  public void containsWithNoMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that(duplicate).is(Contains("three"), invalid("INVALID"))
        .orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void notContainsWithMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Verify.that(duplicate).is(NotContains("three"), invalid("INVALID"))
        .orElseThrow();
  }

  @Test
  public void notContainsWithNoMatchingElement() {
    List<String> duplicate = Arrays.asList("one", "two");

    Assertions.assertThatThrownBy(() -> Verify.that(duplicate).is(NotContains("one"), invalid("INVALID"))
        .orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }

}