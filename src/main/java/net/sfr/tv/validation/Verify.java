package net.sfr.tv.validation;

public interface Verify {

  static <T> Validated<T> that(T actual) {
    return Is.valid(actual);
  }
}
