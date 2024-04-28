package tailspin.language.nodes.transform;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.GetNextStreamValueNode;
import tailspin.language.nodes.value.LocalDefinitionNode;
import tailspin.language.runtime.ResultIterator;

public class ChainStageNode extends ValueNode {

  private final int resultSlot;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private LoopNode loop;

  private ChainStageNode(int valuesSlot, int cvSlot, ValueNode stage, int resultSlot) {
    this.resultSlot = resultSlot;
    loop = Truffle.getRuntime().createLoopNode(new ChainStageRepeatingNode(valuesSlot, cvSlot, stage, resultSlot));
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    loop.execute(frame);
    Object results = frame.getObjectStatic(resultSlot);
    frame.setObjectStatic(resultSlot, null);
    return results;
  }

  static ChainStageNode create(int chainValuesSlot, int chainCvSlot, ValueNode stage, int chainResultSlot) {
    return new ChainStageNode(chainValuesSlot, chainCvSlot,
        stage, chainResultSlot);
  }

  private static class ChainStageRepeatingNode extends Node implements RepeatingNode {
    int resultSlot;
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private StatementNode setCurrentValue;
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    ValueNode stage;

    ChainStageRepeatingNode(int chainValuesSlot, int chainCvSlot, ValueNode stage, int resultSlot) {
      this.setCurrentValue = LocalDefinitionNode.create(GetNextStreamValueNode.create(chainValuesSlot), chainCvSlot);
      this.stage = stage;
      this.resultSlot = resultSlot;
    }

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
      try {
        setCurrentValue.executeVoid(frame);
      } catch (EndOfStreamException e) {
        return false;
      }
      Object previous = frame.getObjectStatic(resultSlot);
      Object result = stage.executeGeneric(frame);
      frame.setObjectStatic(resultSlot, ResultIterator.merge(previous, result));
      return true;
    }
  }
}
