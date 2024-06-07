package tailspin.language.parser.composer;

public interface Value<T> {
  T resolve(Scope scope);
}
