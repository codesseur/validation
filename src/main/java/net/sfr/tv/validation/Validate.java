package net.sfr.tv.validation;

public interface Validate {

  static <T> Validated<T> that(T actual) {
    return Is.valid(actual);
  }
}
