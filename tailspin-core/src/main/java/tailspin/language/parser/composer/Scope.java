package tailspin.language.parser.composer;

public interface Scope {

  void defineValue(String identifier, Object value);

  void undefineValue(String identifier);

  Object getValue(String identifier);
}
