package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import java.util.List;
import tailspin.language.nodes.ExpressionNode;

public class ChainNode extends ExpressionNode {
  private final int valuesSlot;
  @Children
  private final ExpressionNode[] stages;

  public ChainNode(int valuesSlot, List<ExpressionNode> stages) {
    this.valuesSlot = valuesSlot;
    this.stages = stages.toArray(new ExpressionNode[0]);
  }

  @Override
  @ExplodeLoop
  public Object executeGeneric(VirtualFrame frame) {
    for (int i = 0; i < stages.length - 1; i++) {
      frame.setObjectStatic(valuesSlot, stages[i].executeGeneric(frame));
    }
    Object result = stages[stages.length - 1].executeGeneric(frame);
    frame.clearObjectStatic(valuesSlot);
    return result;
  }
}
