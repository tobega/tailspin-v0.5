package tailspin.language.nodes.iterate;

import static com.oracle.truffle.api.CompilerDirectives.castExact;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import java.util.ArrayList;

public abstract class SetResultNode extends Node {

  abstract void execute(VirtualFrame frame, Object result);

  public static class SetSingleResultNode extends SetResultNode {

    private final int resultSlot;

    public SetSingleResultNode(int resultSlot) {
      this.resultSlot = resultSlot;
    }

    void execute(VirtualFrame frame, Object result) {
      Object previous = frame.getObjectStatic(resultSlot);
      if (previous != null) throw new NotSingleResultException(previous);
      frame.setObjectStatic(resultSlot, result);
    }
  }

  public static class SetStreamResultNode extends SetResultNode {

    private final int resultSlot;

    public SetStreamResultNode(int resultSlot) {
      this.resultSlot = resultSlot;
    }

    void execute(VirtualFrame frame, Object result) {
      @SuppressWarnings("unchecked")
      ArrayList<Object> previous = castExact(frame.getObjectStatic(resultSlot), ArrayList.class);
      addToPrevious(previous, result);
    }

    @TruffleBoundary
    private void addToPrevious(ArrayList<Object> previous, Object result) {
      if (result != null) {
        if (result instanceof ArrayList<?> results) {
          previous.addAll(results);
        } else {
          previous.add(result);
        }
      }
    }
  }
}
