package tailspin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.graalvm.polyglot.Context;
import org.junit.jupiter.api.Test;

public class StringInputTest {

  @Test
  void helloYou() {
    try (Context truffleContext = Context.create()) {
      truffleContext.getPolyglotBindings().putMember("input", "you");
      assertEquals("Hello you!", truffleContext.eval("tt", "'Hello $BINDINGS::input;!' !"));
    }
  }
}
