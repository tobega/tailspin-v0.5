package tailspin.language.parser.composer;

import java.util.Objects;
import tailspin.language.parser.ParseNodeScope;

public interface Value<T> {
  T resolve(ParseNodeScope scope);

  class Constant<T> implements Value<T> {
    private final T value;

    public Constant(T value) {
      this.value = value;
    }

    @Override
    public T resolve(ParseNodeScope scope) {
      return value;
    }

    @Override
    public String toString() {
      return Objects.toString(value);
    }
  }

  class Reference<T> implements Value<T> {

    private final String identifier;

    public Reference(String identifier) {
      this.identifier = identifier;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T resolve(ParseNodeScope scope) {
      return (T) scope.getValue(identifier);
    }

    @Override
    public String toString() {
      return "$" + identifier;
    }
  }
}
