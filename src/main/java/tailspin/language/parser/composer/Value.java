package tailspin.language.parser.composer;

import java.util.Objects;

public interface Value<T> {
  T resolve(Scope scope);

  class Constant<T> implements Value<T> {
    private final T value;

    public Constant(T value) {
      this.value = value;
    }

    @Override
    public T resolve(Scope scope) {
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
    public T resolve(Scope scope) {
      return (T) scope.getValue(identifier);
    }

    @Override
    public String toString() {
      return "$" + identifier;
    }
  }
}
