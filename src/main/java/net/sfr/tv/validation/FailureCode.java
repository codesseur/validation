package net.sfr.tv.validation;

import org.immutables.value.Value;

@Value.Immutable
public interface FailureCode {

  String message();
}
