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

  @Test
  public void index() {
    try (Context truffleContext =
        Context.newBuilder()
            .allowPolyglotAccess(PolyglotAccess.ALL)
            .allowHostAccess(HostAccess.ALL)
            .build()) {
      truffleContext.getPolyglotBindings().putMember("input", List.of(3L, 5L, 6L));
      assertEquals(
          5L, truffleContext.eval("tt", "$BINDINGS::input -> $(1) !").asLong());
    }
  }

  @Test
  public void selection() {
    try (Context truffleContext =
        Context.newBuilder()
            .allowPolyglotAccess(PolyglotAccess.ALL)
            .allowHostAccess(HostAccess.ALL)
            .build()) {
      truffleContext.getPolyglotBindings().putMember("input", List.of(3L, 5L, 6L, 8L));
      assertEquals(
          "[8, 3]", truffleContext.eval("tt", "$BINDINGS::input -> $([3,0]) !").toString());
    }
  }

  @Test
  public void selector() {
    try (Context truffleContext =
        Context.newBuilder()
            .allowPolyglotAccess(PolyglotAccess.ALL)
            .allowHostAccess(HostAccess.ALL)
            .build()) {
      truffleContext.getPolyglotBindings().putMember("input", List.of(3L, 2L));
      assertEquals(
          "[8, 3]", truffleContext.eval("tt", " [1, 3, 8, 9] -> $($BINDINGS::input) !").toString());
    }
  }

  @Test
  public void selectionAndSelector() {
    try (Context truffleContext =
        Context.newBuilder()
            .allowPolyglotAccess(PolyglotAccess.ALL)
            .allowHostAccess(HostAccess.ALL)
            .build()) {
      truffleContext.getPolyglotBindings().putMember("input", List.of(0L, 1L, 3L, 8L));
      truffleContext.getPolyglotBindings().putMember("select", List.of(3L, 2L));
      assertEquals(
          "[8, 3]", truffleContext.eval("tt", " $BINDINGS::input -> $($BINDINGS::select) !").toString());
    }
  }

  @Test
  public void slice() {
    try (Context truffleContext =
        Context.newBuilder()
            .allowPolyglotAccess(PolyglotAccess.ALL)
            .allowHostAccess(HostAccess.ALL)
            .build()) {
      truffleContext.getPolyglotBindings().putMember("input", List.of(3L, 5L, 6L, 8L));
      assertEquals(
          "[5, 6]", truffleContext.eval("tt", "$BINDINGS::input -> $(1..2) !").toString());
    }
  }
}
