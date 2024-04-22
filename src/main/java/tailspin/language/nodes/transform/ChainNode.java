package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import java.util.List;
import tailspin.language.nodes.ValueNode;

public class ChainNode extends ValueNode {
  private final int valuesSlot;
  @Children
  private final ValueNode[] stages;

  public ChainNode(int valuesSlot, List<ValueNode> stages) {
    this.valuesSlot = valuesSlot;
    this.stages = stages.toArray(new ValueNode[0]);
  }

  @Override
  @ExplodeLoop
  public Object executeGeneric(VirtualFrame frame) {
    for (int i = 0; i < stages.length - 1; i++) {
      frame.setObjectStatic(valuesSlot, stages[i].executeGeneric(frame));
    }
    Object result = stages[stages.length - 1].executeGeneric(frame);
    frame.setObjectStatic(valuesSlot, null);
    return result;
  }
}
