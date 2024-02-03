package tailspin.language;

public class TypeError extends Error {
  public TypeError(String message) {
    super(message);
  }

  public static TypeError at(ExpressionNode expr, Object... values) {
    return new TypeError("oops!");
  }
}
