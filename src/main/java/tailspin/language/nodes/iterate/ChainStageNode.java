package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.profiles.CountingConditionProfile;
import java.util.ArrayList;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.ChainStageNodeGen.GetNextStreamValueNodeGen;
import tailspin.language.nodes.iterate.ChainStageNodeGen.SetChainCvNodeGen;

public abstract class ChainStageNode extends TransformNode {

  private final int valuesSlot;
  protected final int cvSlot;
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected StaticReferenceNode values;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private TransformNode stage;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private LoopNode loop;

  protected ChainStageNode(int valuesSlot, int cvSlot, TransformNode stage) {
    this.valuesSlot = valuesSlot;
    this.cvSlot = cvSlot;
    this.stage = stage;
    values = StaticReferenceNode.create(valuesSlot);
    loop = Truffle.getRuntime().createLoopNode(new ChainStageRepeatingNode(valuesSlot, cvSlot, stage));
  }

  @Override
  public void setResultSlot(int resultSlot) {
    stage.setResultSlot(resultSlot);
  }

  @Specialization
  public void doLong(VirtualFrame frame, long value, @Shared("cvSetters") @Cached(parameters = "cvSlot") SetChainCvNode setCv) {
    setCv.setLong(frame, value);
    frame.setObjectStatic(valuesSlot, null);
    stage.executeTransform(frame);
  }

  @Specialization(guards = "value == null")
  @SuppressWarnings("unused")
  public void doNull(VirtualFrame frame, Object value) {
    // Do nothing
  }

  @Specialization(guards = "stream != null")
  @SuppressWarnings("unused")
  public void doValueStream(VirtualFrame frame, ArrayList<?> stream) {
    loop.execute(frame);
    frame.setObjectStatic(valuesSlot, null);
  }

  @Specialization
  @SuppressWarnings("unused")
  public void doSingle(VirtualFrame frame, Object value, @Shared("cvSetters") @Cached(parameters = "cvSlot") SetChainCvNode setCv) {
    setCv.execute(frame, value);
    frame.setObjectStatic(valuesSlot, null);
    stage.executeTransform(frame);
  }

  static ChainStageNode create(int chainValuesSlot, int chainCvSlot, TransformNode stage) {
    return ChainStageNodeGen.create(chainValuesSlot, chainCvSlot, stage);
  }

  private static class ChainStageRepeatingNode extends Node implements RepeatingNode {
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private SetChainCvNode setCurrentValue;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private GetNextStreamValueNode getNextStreamValueNode;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    TransformNode stage;

    ChainStageRepeatingNode(int chainValuesSlot, int chainCvSlot, TransformNode stage) {
      this.setCurrentValue = SetChainCvNode.create(chainCvSlot);
      this.getNextStreamValueNode = GetNextStreamValueNode.create(chainValuesSlot);
      this.stage = stage;
    }

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
      try {
        setCurrentValue.execute(frame, getNextStreamValueNode.executeStream(frame));
      } catch (EndOfStreamException e) {
        return false;
      }
      stage.executeTransform(frame);
      return true;
    }
  }

  @NodeField(name = "slot", type = int.class)
  public abstract static class SetChainCvNode extends Node {
    protected abstract int getSlot();

    public abstract void execute(VirtualFrame frame, Object value);

    @Specialization
    void setLong(VirtualFrame frame, long value) {
      frame.getFrameDescriptor().setSlotKind(getSlot(), FrameSlotKind.Long);
      frame.setLong(getSlot(), value);
    }

    @Specialization
    void setObject(VirtualFrame frame, Object value) {
      frame.getFrameDescriptor().setSlotKind(getSlot(), FrameSlotKind.Object);
      frame.setObject(getSlot(), value);
    }

    @NeverDefault
    public static SetChainCvNode create(int slot) {
      return SetChainCvNodeGen.create(slot);
    }
  }

  @SuppressWarnings("unused")
  @NodeChild(value = "valueStream", type = StaticReferenceNode.class)
  public abstract static class GetNextStreamValueNode extends Node {
    public abstract Object executeStream(VirtualFrame frame);

    final CountingConditionProfile emptyProfile = CountingConditionProfile.create();

    @Specialization(guards = {"stream != null"})
    public Object doStream(ArrayList<?> stream) {
      if (emptyProfile.profile(stream.isEmpty())) {
        throw new EndOfStreamException();
      }
      return stream.removeFirst();
    }

    public static GetNextStreamValueNode create(int valuesSlot) {
      return GetNextStreamValueNodeGen.create(StaticReferenceNode.create(valuesSlot));
    }
  }

  public static class StaticReferenceNode extends ValueNode {

    private final int slot;

    private StaticReferenceNode(int slot) {
      this.slot = slot;
    }

    public static StaticReferenceNode create(int valuesSlot) {
      return new StaticReferenceNode(valuesSlot);
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
      return frame.getObjectStatic(slot);
    }
  }
}
