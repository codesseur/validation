package net.sfr.tv.validation;

import static net.sfr.tv.validation.ToBe.invalid;
import static net.sfr.tv.validation.condition.ContainerConditions.SubsetOf;

import java.util.Arrays;
import java.util.HashSet;
import net.sfr.tv.mixin.iterate.container.Bag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyContainerTest {

  @Test
  public void isSubsetOf_passedTest() {
    Bag<String> set = () -> new HashSet<>(Arrays.asList("one", "two", "three"));
    Bag<String> subSet = () -> new HashSet<>(Arrays.asList("one", "two"));

    Validate.that(subSet).is(SubsetOf(set), invalid("INVALID")).orElseThrow();
  }

  @Test
  public void isSubsetOf_noPassedTest() {
    Bag<String> set = () -> new HashSet<>(Arrays.asList("one", "two", "three"));
    Bag<String> subSet = () -> new HashSet<>(Arrays.asList("one", "two", "three", "four"));

    Assertions.assertThatThrownBy(() -> Validate.that(subSet).is(SubsetOf(set), invalid("INVALID")).orElseThrow())
        .isInstanceOf(RuntimeException.class);
  }
}