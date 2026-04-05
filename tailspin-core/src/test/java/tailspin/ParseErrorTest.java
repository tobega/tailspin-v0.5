package tailspin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.junit.jupiter.api.Test;

public class ParseErrorTest {
  @Test
  public void testParseError() {
    try (Context truffleContext =
        Context.newBuilder().build()) {
      truffleContext.eval("tt", """
        foo templates
          @: 5;
          $@ !
        end foo
        """);
    } catch (PolyglotException e) {
      String message = e.getMessage();
      assertEquals(
          """
        java.lang.AssertionError: Parse failed at
        : 5;
          $@ !
        end foo
        after
        foo templates
          @""",
          message);
    }
  }
}
