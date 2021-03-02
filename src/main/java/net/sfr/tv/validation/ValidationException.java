package net.sfr.tv.validation;

public class ValidationException extends RuntimeException {

  private final Failure root;
  private final Failures details;

  public ValidationException(Failures details) {
    this.root = details.findFirst().orElse(null);
    this.details = details;
  }

  public ValidationException(Failure root, Failures details) {
    this.root = root;
    this.details = details;
  }

  public Failure getRoot() {
    return root;
  }

  public Failures getDetails() {
    return details;
  }

  @Override
  public String toString() {
    return "ValidationException{" +
        "root=" + root +
        ", details=" + details +
        '}';
  }
}
