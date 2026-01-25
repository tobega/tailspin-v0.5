package tailspin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.graalvm.polyglot.Context;
import org.junit.jupiter.api.Test;

public class StringInput {

  @Test
  void helloYou() {
    try (Context truffleContext = Context.create()) {
      assertEquals("Hello you!", truffleContext.eval("tt", "'Hello $input;!' !"));
    }
  }
}
