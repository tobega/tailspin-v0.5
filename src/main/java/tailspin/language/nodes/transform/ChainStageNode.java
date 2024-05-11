package tailspin.language.nodes.transform;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.GetNextStreamValueNode;
import tailspin.language.nodes.value.LocalDefinitionNode;
import tailspin.language.nodes.value.StaticReferenceNode;
import tailspin.language.runtime.ResultIterator;
import tailspin.language.runtime.ValueStream;

public abstract class ChainStageNode extends ValueNode {

  private final int valuesSlot;
  private final int cvSlot;
  private final int resultSlot;
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected StaticReferenceNode values;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private ValueNode stage;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private LoopNode loop;

  protected ChainStageNode(int valuesSlot, int cvSlot, ValueNode stage, int resultSlot) {
    this.valuesSlot = valuesSlot;
    this.cvSlot = cvSlot;
    this.stage = stage;
    this.resultSlot = resultSlot;
    values = StaticReferenceNode.create(valuesSlot);
    loop = Truffle.getRuntime().createLoopNode(new ChainStageRepeatingNode(valuesSlot, cvSlot, stage, resultSlot));
  }

  @Specialization
  public Object doLong(VirtualFrame frame, long value) {
    frame.getFrameDescriptor().setSlotKind(cvSlot, FrameSlotKind.Long);
    frame.setLong(cvSlot, value);
    frame.setObjectStatic(valuesSlot, null);
    return stage.executeGeneric(frame);
  }

  @Specialization(guards = "value == null")
  @SuppressWarnings("unused")
  public Object doNull(VirtualFrame frame, Object value) {
    return null;
  }

  @Specialization(guards = "stream != null")
  @SuppressWarnings("unused")
  public Object doValueStream(VirtualFrame frame, ValueStream stream) {
    loop.execute(frame);
    Object results = frame.getObjectStatic(resultSlot);
    frame.setObjectStatic(resultSlot, null);
    return results;
  }

  @Specialization(guards = {"value != null"})
  @SuppressWarnings("unused")
  public Object doSingle(VirtualFrame frame, Object value) {
    frame.getFrameDescriptor().setSlotKind(cvSlot, FrameSlotKind.Object);
    frame.setObject(cvSlot, value);
    frame.setObjectStatic(valuesSlot, null);
    return stage.executeGeneric(frame);
  }

  static ChainStageNode create(int chainValuesSlot, int chainCvSlot, ValueNode stage, int chainResultSlot) {
    return ChainStageNodeGen.create(chainValuesSlot, chainCvSlot,
        stage, chainResultSlot);
  }

  private static class ChainStageRepeatingNode extends Node implements RepeatingNode {
    int resultSlot;
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private LocalDefinitionNode setCurrentValue;
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
