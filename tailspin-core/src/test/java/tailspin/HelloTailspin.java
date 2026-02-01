package tailspin;

import org.graalvm.polyglot.Context;

public class HelloTailspin {
  public static void main(String[] args) {
    try (Context truffleContext = Context.create()) {
      System.out.println(truffleContext.eval("tt", "'Hello Tailspin!' !"));
    }
  }
}
