package tailspin.language.parser.composer;

import java.util.HashMap;
import java.util.Map;

public class Scope {

  private final Scope parent;
  private final Map<String, Object> definitions = new HashMap<>();

  public Scope(Scope scope) {
    this.parent = scope;
  }

  public void defineValue(String identifier, Object value) {
    definitions.put(identifier, value);
  }

  public void undefineValue(String identifier) {
    definitions.remove(identifier);
  }

  public Object getValue(String identifier) {
    if (definitions.containsKey(identifier)) return definitions.get(identifier);
    return parent.getValue(identifier);
  }
}
