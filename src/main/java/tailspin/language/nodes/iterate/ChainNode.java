package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import java.util.List;
import tailspin.language.nodes.ValueNode;

public abstract class ChainNode extends ValueNode {
  private final int valuesSlot;
  @Children
  private final ValueNode[] stages;
  private final int isFirstSlot;

  ChainNode(int valuesSlot, int cvSlot, int resultSlot, List<ValueNode> stages, int isFirstSlot) {
    this.valuesSlot = valuesSlot;
    this.stages = stages.toArray(new ValueNode[0]);
    this.isFirstSlot = isFirstSlot;
    for (int i = 1; i < this.stages.length; i++) {
      this.stages[i] = ChainStageNode.create(valuesSlot, cvSlot, this.stages[i], resultSlot, isFirstSlot);
    }
  }

  public static ChainNode create(int chainValuesSlot, int chainCvSlot, int chainResultSlot, List<ValueNode> stages, int isFirstSlot) {
    return ChainNodeGen.create(chainValuesSlot, chainCvSlot, chainResultSlot, stages, isFirstSlot);
  }

  @Specialization
  @ExplodeLoop
  public Object doChain(VirtualFrame frame, @Cached(inline = true) SetFirstNode setFirstNode) {
    for (int i = 0; i < stages.length - 1; i++) {
      setFirstNode.execute(frame, this, isFirstSlot, true);
      frame.setObjectStatic(valuesSlot, stages[i].executeGeneric(frame));
    }
    setFirstNode.execute(frame, this, isFirstSlot, true);
    Object result = stages[stages.length - 1].executeGeneric(frame);
    frame.setObjectStatic(valuesSlot, null);
    return result;
  }
}
