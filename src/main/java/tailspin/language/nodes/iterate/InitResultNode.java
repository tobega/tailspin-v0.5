package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.runtime.ResultIterator;

public abstract class InitResultNode extends Node {

  abstract void execute(VirtualFrame frame);

  public static class InitSingleResultNode extends InitResultNode {

    private final int resultSlot;

    public InitSingleResultNode(int resultSlot) {
      this.resultSlot = resultSlot;
    }

    void execute(VirtualFrame frame) {
      frame.setObjectStatic(resultSlot, null);
    }
  }

  public static class InitStreamResultNode extends InitResultNode {

    private final int resultSlot;

    public InitStreamResultNode(int resultSlot) {
      this.resultSlot = resultSlot;
    }

    void execute(VirtualFrame frame) {
      frame.setObjectStatic(resultSlot, ResultIterator.empty());
    }
  }
}