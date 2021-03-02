@Style(
    overshadowImplementation = true,
    deepImmutablesDetection = true,
    depluralize = true,
    optionalAcceptNullable = true,
    allParameters = true,
    throwForNullPointer = ImmutablesEmptyValue.class,
    throwForInvalidImmutableState = ImmutablesInvalidValue.class
)
package net.sfr.tv.validation;

import net.sfr.tv.ImmutablesEmptyValue;
import net.sfr.tv.ImmutablesInvalidValue;
import org.immutables.value.Value.Style;