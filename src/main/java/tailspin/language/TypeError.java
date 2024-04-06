package tailspin.language;

import tailspin.language.nodes.TailspinNode;

public class TypeError extends Error {
  public TypeError(String message) {
    super(message);
  }

  @SuppressWarnings("unused")
  public static TypeError at(TailspinNode node, Object... values) {
    return new TypeError("oops!");
  }
}
