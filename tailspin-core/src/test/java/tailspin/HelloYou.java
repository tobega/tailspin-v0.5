package tailspin;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotAccess;

public class HelloYou {
  public static void main(String[] args) {
    try (Context truffleContext = Context.newBuilder().allowPolyglotAccess(PolyglotAccess.ALL).build()) {
      truffleContext.getPolyglotBindings().putMember("input", "you");
      System.out.println(truffleContext.eval("tt", "'Hello $BINDINGS::input;!' !"));
    }
  }
}
