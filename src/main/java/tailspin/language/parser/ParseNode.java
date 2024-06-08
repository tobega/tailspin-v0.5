package tailspin.language.parser;

import java.util.List;
import java.util.Objects;

public record ParseNode(String name, Object content) {

  public static Object normalizeValues(Object values) {
    if (values == null) {
      return null;
    } else if (values instanceof List<?> list) {
      list = list.stream().filter(Objects::nonNull).toList();
      if (list.isEmpty())
        return null;
      if (list.size() == 1) {
        values = list.getFirst();
      } else {
        values = list;
      }
    }
    return values;
  }
}
