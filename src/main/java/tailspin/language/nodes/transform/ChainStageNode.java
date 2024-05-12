package tailspin.language.nodes.transform;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NeverDefault;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.transform.ChainStageNodeGen.GetNextStreamValueNodeGen;
import tailspin.language.nodes.transform.ChainStageNodeGen.SetChainCvNodeGen;
import tailspin.language.runtime.ResultIterator;
import tailspin.language.runtime.ValueStream;

public abstract class ChainStageNode extends ValueNode {

  private final int valuesSlot;
  protected final int cvSlot;
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
  public Object doLong(VirtualFrame frame, long value, @Shared("cvSetters") @Cached(parameters = "cvSlot") SetChainCvNode setCv) {
    setCv.setLong(frame, value);
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
  public Object doSingle(VirtualFrame frame, Object value, @Shared("cvSetters") @Cached(parameters = "cvSlot") SetChainCvNode setCv) {
    setCv.execute(frame, value);
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
    private SetChainCvNode setCurrentValue;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private GetNextStreamValueNode getNextStreamValueNode;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    ValueNode stage;

    ChainStageRepeatingNode(int chainValuesSlot, int chainCvSlot, ValueNode stage, int resultSlot) {
      this.setCurrentValue = SetChainCvNode.create(chainCvSlot);
      this.getNextStreamValueNode = GetNextStreamValueNode.create(chainValuesSlot);
      this.stage = stage;
      this.resultSlot = resultSlot;
    }

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
      try {
        setCurrentValue.execute(frame, getNextStreamValueNode.executeStream(frame));
      } catch (EndOfStreamException e) {
        return false;
      }
      Object previous = frame.getObjectStatic(resultSlot);
      Object result = stage.executeGeneric(frame);
      frame.setObjectStatic(resultSlot, ResultIterator.merge(previous, result));
      return true;
    }
  }

  @NodeField(name = "slot", type = int.class)
  public abstract static class SetChainCvNode extends Node {
    protected abstract int getSlot();

    abstract void execute(VirtualFrame frame, Object value);

    @Specialization
    void setLong(VirtualFrame frame, long value) {
      frame.getFrameDescriptor().setSlotKind(getSlot(), FrameSlotKind.Long);
      frame.setLong(getSlot(), value);
    }

    @Fallback
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

    @Specialization(guards = {"stream != null"})
    @TruffleBoundary
    public Object doStream(ValueStream stream) {
      try {
        if (!stream.hasIteratorNextElement()) {
          throw new EndOfStreamException();
        }
        return stream.getIteratorNextElement();
      } catch (StopIterationException e) {
        throw new RuntimeException(e);
      }
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
