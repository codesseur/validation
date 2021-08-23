package com.codesseur.validation;

public class ValidationException extends RuntimeException {

  private final Failures details;

  public ValidationException(Failures details) {
    this.details = details;
  }

  public Failures details() {
    return details;
  }

  @Override
  public String toString() {
    return "ValidationException{details=" + details + '}';
  }
}
