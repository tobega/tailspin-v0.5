package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import java.util.List;
import tailspin.language.nodes.TransformNode;

public class ChainNode extends TransformNode {
  private final int valuesSlot;
  @Children
  private final TransformNode[] stages;

  public ChainNode(int valuesSlot, List<TransformNode> stages) {
    this.valuesSlot = valuesSlot;
    this.stages = stages.toArray(new TransformNode[0]);
  }

  @Override
  @ExplodeLoop
  public Object executeTransform(VirtualFrame frame) {
    for (int i = 0; i < stages.length - 1; i++) {
      frame.setObjectStatic(valuesSlot, stages[i].executeTransform(frame));
    }
    Object result = stages[stages.length - 1].executeTransform(frame);
    frame.setObjectStatic(valuesSlot, null);
    return result;
  }
}
