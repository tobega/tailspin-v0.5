package tailspin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotAccess;
import org.junit.jupiter.api.Test;

public class ListInputTest {
  @Test
  public void stream() {
    try (Context truffleContext =
        Context.newBuilder()
            .allowPolyglotAccess(PolyglotAccess.ALL)
            .allowHostAccess(HostAccess.ALL)
            .build()) {
      truffleContext.getPolyglotBindings().putMember("input", List.of(3L, 5L, 6L));
      assertEquals(
          "4, 6, 7", truffleContext.eval("tt", "$BINDINGS::input ... -> $ + 1 !").toString());
    }
  }

  @Test
  public void iterator() {
    try (Context truffleContext =
        Context.newBuilder()
            .allowPolyglotAccess(PolyglotAccess.ALL)
            .allowHostAccess(HostAccess.ALL)
            .build()) {
      truffleContext.getPolyglotBindings().putMember("input", List.of(3L, 5L, 6L).iterator());
      assertEquals(
          "4, 6, 7", truffleContext.eval("tt", "$BINDINGS::input -> $ + 1 !").toString());
    }
  }
}
