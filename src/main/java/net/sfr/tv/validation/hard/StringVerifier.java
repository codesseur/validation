package net.sfr.tv.validation.hard;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.immutables.value.Value;

@Value.Immutable
public abstract class StringVerifier extends GenericVerifier<String, StringVerifier> {

  public OrElse<String> isNotEmpty() {
    return violates(String::isEmpty);
  }

  public OrElse<String> isNotBlank() {
    return violates(s -> s.trim().isEmpty());
  }

  public OrElse<String> matchCaseSensitive(String regex) {
    return match(Pattern.compile(regex));
  }

  public OrElse<String> matchCaseInsensitive(String regex) {
    return match(Pattern.compile(regex, CASE_INSENSITIVE));
  }


  private OrElse<String> match(Pattern pattern) {
    return addContext("pattern", pattern)
        .satisfiesWithOptional(v -> v.map(pattern::matcher).filter(Matcher::find).isPresent());
  }

  @Override
  public StringVerifier addContext(String key, Object value) {
    return ImmutableStringVerifier.builder().from(this).context(context().add(key, value)).build();
  }

  @Override
  public StringVerifier label(String label) {
    return ImmutableStringVerifier.builder().from(this).label(label).build();
  }
}
