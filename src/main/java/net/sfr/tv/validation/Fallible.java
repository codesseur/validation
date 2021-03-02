package net.sfr.tv.validation;

import org.immutables.value.Value;

public interface Fallible {

  @Value.Default
  default Failures failures() {
    return Failures.empty();
  }

}
