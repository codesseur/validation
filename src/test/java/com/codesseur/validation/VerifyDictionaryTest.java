package com.codesseur.validation;

import com.codesseur.iterate.container.Dictionary;
import io.vavr.Tuple;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyDictionaryTest {

  private final Dictionary<String, String> one = Dictionary.of(Tuple.of("k1", "v1"));
  private final Dictionary<String, String> empty = Dictionary.of();

  @Test
  public void isNotEmptyWithNoneEmptyMap() {
    Verify.that(one).isNotEmpty().otherwiseThrow();
  }

  @Test
  public void isNotEmptyWithEmptyMap() {
    Assertions.assertThatThrownBy(() -> Verify.that(empty).isNotEmpty().otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void emptyWithNoneEmptyMap() {
    Verify.that(empty).isEmpty().otherwiseThrow();
  }

  @Test
  public void emptyWithEmptyMap() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).isEmpty().otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void containsAnyKeysKo() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).containsAnyKeys("k2").otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void containsAnyKeysOk() {
    Verify.that(one).containsAnyKeys("k1").otherwiseThrow();
  }

  @Test
  public void containsAllKeysKo() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).containsAllKeys("k2").otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void containsAllKeysOk() {
    Verify.that(one).containsAllKeys("k1").otherwiseThrow();
  }

  @Test
  public void notContainsAnyKeysKo() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).notContainsAnyKeys("k1").otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void notContainsAnyKeysOk() {
    Verify.that(one).notContainsAnyKeys("k2").otherwiseThrow();
  }

  @Test
  public void hasSizeKo() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).hasSize(2).otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void hasSizeOk() {
    Verify.that(one).hasSize(1).otherwiseThrow();
  }

  @Test
  public void containsKeyKo() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).containsKey("k2").otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void containsKeyOk() {
    Verify.that(one).containsKey("k1").otherwiseThrow();
  }

  @Test
  public void notContainsKeyKo() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).notContainsKey("k1").otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void notContainsKeyOk() {
    Verify.that(one).notContainsKey("k2").otherwiseThrow();
  }

  @Test
  public void anyKeysMatchKo() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).anyKeyMatches(k -> k.equals("k2")).otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void anyKeysMatchOk() {
    Verify.that(one).anyKeyMatches(k -> k.equals("k1")).otherwiseThrow();
  }

  @Test
  public void allKeysMatchKo() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).allKeysMatch(k -> k.equals("k2")).otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void allKeysMatchOk() {
    Verify.that(one).allKeysMatch(k -> k.equals("k1")).otherwiseThrow();
  }

  @Test
  public void anyValuesMatchKo() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).anyValueMatches(k -> k.equals("v2")).otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void anyValuesMatchOk() {
    Verify.that(one).anyValueMatches(k -> k.equals("v1")).otherwiseThrow();
  }

  @Test
  public void allValuesMatchKo() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).allValuesMatch(k -> k.equals("v2")).otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void allValuesMatchOk() {
    Verify.that(one).allValuesMatch(k -> k.equals("v1")).otherwiseThrow();
  }

  @Test
  public void noneValuesMatchKo() {
    Assertions.assertThatThrownBy(() -> Verify.that(one).noneValuesMatch(k -> k.equals("v1")).otherwiseThrow())
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void noneValuesMatchOk() {
    Verify.that(one).noneValuesMatch(k -> k.equals("v2")).otherwiseThrow();
  }
}