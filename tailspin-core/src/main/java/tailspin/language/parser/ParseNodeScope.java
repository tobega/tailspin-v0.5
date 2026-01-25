package tailspin.language.parser;

import static tailspin.language.parser.ParseNode.normalizeValues;

import java.util.HashMap;
import java.util.Map;

public class ParseNodeScope implements tailspin.language.parser.composer.Scope {

  private final ParseNodeScope parent;
  private final Map<String, Object> definitions = new HashMap<>();

  public ParseNodeScope(ParseNodeScope scope) {
    this.parent = scope;
  }

  @Override
  public void defineValue(String identifier, Object value) {
    while (value instanceof ParseNode p) {
      value = normalizeValues(p.content());
    }
    definitions.put(identifier, value);
  }

  @Override
  public void undefineValue(String identifier) {
    definitions.remove(identifier);
  }

  @Override
  public Object getValue(String identifier) {
    if (definitions.containsKey(identifier)) return definitions.get(identifier);
    return parent.getValue(identifier);
  }
}
