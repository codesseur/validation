package net.sfr.tv.validation.condition;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static net.sfr.tv.validation.condition.ObjectConditions.fromPredicateNonNull;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.function.Function;
import java.util.regex.Pattern;
import net.sfr.tv.validation.Status;

public interface StringConditions{

  @SafeVarargs
  static Function<String, Status> NotEmpty(Tuple2<String, Object>... context) {
    return fromPredicateNonNull(v -> !v.isEmpty(), context);
  }

  @SafeVarargs
  static Function<String, Status> NotBlank(Tuple2<String, Object>... context) {
    return fromPredicateNonNull(s -> !s.trim().isEmpty(), context);
  }

  @SafeVarargs
  static Function<String, Status> MatchCaseSensitive(String regex, Tuple2<String, Object>... context) {
    return Match(Pattern.compile(regex), context);
  }

  @SafeVarargs
  static Function<String, Status> MatchCaseInsensitive(String regex, Tuple2<String, Object>... context) {
    return Match(Pattern.compile(regex, CASE_INSENSITIVE), context);
  }

  @SafeVarargs
  private static Function<String, Status> Match(Pattern pattern, Tuple2<String, Object>... context) {
    return fromPredicateNonNull(s -> pattern.matcher(s).find(), context, Tuple.of("_expectedPattern", pattern));
  }

}
