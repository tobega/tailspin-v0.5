package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.CompilerDirectives;
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
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.ChainStageNodeGen.GetNextStreamValueNodeGen;
import tailspin.language.nodes.iterate.ChainStageNodeGen.SetChainCvNodeGen;
import tailspin.language.nodes.iterate.InitResultNode.InitSingleResultNode;
import tailspin.language.nodes.iterate.InitResultNode.InitStreamResultNode;
import tailspin.language.nodes.iterate.SetResultNode.SetSingleResultNode;
import tailspin.language.nodes.iterate.SetResultNode.SetStreamResultNode;
import tailspin.language.nodes.transform.EndOfStreamException;
import tailspin.language.runtime.ResultIterator;

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

  protected ChainStageNode(int valuesSlot, int cvSlot, ValueNode stage, int resultSlot, int isFirstSlot) {
    this.valuesSlot = valuesSlot;
    this.cvSlot = cvSlot;
    this.stage = stage;
    this.resultSlot = resultSlot;
    values = StaticReferenceNode.create(valuesSlot);
    loop = Truffle.getRuntime().createLoopNode(new ChainStageRepeatingNode(valuesSlot, cvSlot, stage, resultSlot, isFirstSlot));
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
  public Object doValueStream(VirtualFrame frame, ResultIterator stream) {
    loop.execute(frame);
    Object results = frame.getObjectStatic(resultSlot);
    frame.setObjectStatic(resultSlot, null);
    return results;
  }

  @Specialization
  @SuppressWarnings("unused")
  public Object doSingle(VirtualFrame frame, Object value, @Shared("cvSetters") @Cached(parameters = "cvSlot") SetChainCvNode setCv) {
    setCv.execute(frame, value);
    frame.setObjectStatic(valuesSlot, null);
    return stage.executeGeneric(frame);
  }

  static ChainStageNode create(int chainValuesSlot, int chainCvSlot, ValueNode stage, int chainResultSlot, int isFirstSlot) {
    return ChainStageNodeGen.create(chainValuesSlot, chainCvSlot,
        stage, chainResultSlot, isFirstSlot);
  }

  private static class ChainStageRepeatingNode extends Node implements RepeatingNode {
    final int resultSlot;
    final int isFirstSlot;
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private GetFirstNode getFirstNode;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private SetFirstNode setFirstNode;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private SetChainCvNode setCurrentValue;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private GetNextStreamValueNode getNextStreamValueNode;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    ValueNode stage;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    InitResultNode initResult;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    SetResultNode setResult;

    ChainStageRepeatingNode(int chainValuesSlot, int chainCvSlot, ValueNode stage, int resultSlot, int isFirstSlot) {
      this.getFirstNode = GetFirstNode.create();
      this.setFirstNode = SetFirstNode.create();
      this.setCurrentValue = SetChainCvNode.create(chainCvSlot);
      this.getNextStreamValueNode = GetNextStreamValueNode.create(chainValuesSlot);
      this.stage = stage;
      this.resultSlot = resultSlot;
      this.isFirstSlot = isFirstSlot;
      this.initResult = new InitSingleResultNode(resultSlot);
      this.setResult = new SetSingleResultNode(resultSlot);
    }

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
      if (getFirstNode.execute(frame, this, isFirstSlot)) {
        setFirstNode.execute(frame, this, isFirstSlot, false);
        initResult.execute(frame);
      }
      try {
        setCurrentValue.execute(frame, getNextStreamValueNode.executeStream(frame));
      } catch (EndOfStreamException e) {
        return false;
      }
      Object result = stage.executeGeneric(frame);
      try {
        setResult.execute(frame, result);
      } catch (NotSingleResultException e) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        initResult = initResult.replace(new InitStreamResultNode(resultSlot));
        setResult = setResult.replace(new SetStreamResultNode(resultSlot));
        initResult.execute(frame);
        setResult.execute(frame, e.getPreviousResult());
        setResult.execute(frame, result);
      }
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

    @Specialization(guards = {"stream != null"})
    public Object doStream(ResultIterator stream) {
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
