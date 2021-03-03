package net.sfr.tv.validation.hard;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import org.immutables.value.Value;

@Value.Immutable
public abstract class PathMatcher {

  public static PathMatcher all() {
    return ImmutablePathMatcher.builder().build();
  }

  @Value.Default
  Set<String> includes() {
    return Collections.singleton("**");
  }

  @Value.Default
  Set<String> excludes() {
    return Collections.emptySet();
  }

  public PathMatcher excludes(String... excludes) {
    return ImmutablePathMatcher.builder().from(this).excludes(Arrays.asList(excludes)).build();
  }

  public boolean matches(String path) {
    return path.matches(regex());
  }

  @Value.Derived
  String regex() {
    return
        "^" + includes().stream().map(this::match).collect(joining("|", "(?=", ")"))
            +
            excludes().stream().map(this::unmatch).collect(joining("|", "(?=", ")")) + ".*$";
  }

  private String unmatch(String glob) {
    return "(?!" + match(glob) + ")";
  }

  private String match(String glob) {
    return glob
        .replace(".", "\\.")
        .replace("?", "[^/]")
        .replace("**", ".*")
        .replaceAll("([^.])\\*", "$1[^/]*");
  }

}
