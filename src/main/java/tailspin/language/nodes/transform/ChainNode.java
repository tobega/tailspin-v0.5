package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import java.util.List;
import tailspin.language.nodes.ValueNode;

public class ChainNode extends ValueNode {
  private final int valuesSlot;
  @Children
  private final ValueNode[] stages;

  private ChainNode(int valuesSlot, int cvSlot, int resultSlot, List<ValueNode> stages) {
    this.valuesSlot = valuesSlot;
    this.stages = stages.toArray(new ValueNode[0]);
    for (int i = 1; i < this.stages.length; i++) {
      this.stages[i] = ChainStageNode.create(valuesSlot, cvSlot, this.stages[i], resultSlot);
    }
  }

  public static ChainNode create(int chainValuesSlot, int chainCvSlot, int chainResultSlot, List<ValueNode> stages) {
    return new ChainNode(chainValuesSlot, chainCvSlot, chainResultSlot, stages);
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
