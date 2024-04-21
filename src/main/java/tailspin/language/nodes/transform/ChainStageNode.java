package tailspin.language.nodes.transform;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TransformNode;

public class ChainStageNode extends TransformNode {

  private final int resultSlot;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private LoopNode loop;

  public ChainStageNode(StatementNode setCurrentValue, TransformNode stage, int resultSlot) {
    this.resultSlot = resultSlot;
    loop = Truffle.getRuntime().createLoopNode(new ChainStageRepeatingNode(setCurrentValue, stage, resultSlot));
  }

  @Override
  public Object executeTransform(VirtualFrame frame) {
    loop.execute(frame);
    Object results = frame.getObjectStatic(resultSlot);
    frame.clearObjectStatic(resultSlot);
    return results;
  }

  private static class ChainStageRepeatingNode extends Node implements RepeatingNode {
    int resultSlot;
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private StatementNode setCurrentValue;
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    MergeResultNode mergedResult;

    ChainStageRepeatingNode(StatementNode setCurrentValue, TransformNode stage, int resultSlot) {
      this.setCurrentValue = setCurrentValue;
      this.mergedResult = MergeResultNode.create(resultSlot, stage);
      this.resultSlot = resultSlot;
    }

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
      try {
        setCurrentValue.executeVoid(frame);
      } catch (EndOfStreamException e) {
        return false;
      }
      frame.setObjectStatic(resultSlot, mergedResult.executeTransform(frame));
      return true;
    }
  }
}
