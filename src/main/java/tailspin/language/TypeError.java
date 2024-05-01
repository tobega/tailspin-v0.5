package tailspin.language;

import tailspin.language.nodes.TailspinNode;

public class TypeError extends Error {
  public TypeError(String message) {
    super(message);
  }

  @SuppressWarnings("unused")
  public static TypeError at(TailspinNode node, Object... values) {
    StringBuilder s = new StringBuilder("oops! ");
    for (Object value : values) s.append(value == null ? "null" : value.getClass().getName()).append('\n');
    return new TypeError(s.toString());
  }
}
