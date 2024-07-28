package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.profiles.CountingConditionProfile;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.ChainStageNode.SetChainCvNode;
import tailspin.language.nodes.iterate.RangeIterationNodeGen.RangeIteratorNodeGen;

@NodeChild(value = "start", type = ValueNode.class)
@NodeChild(value = "end", type = ValueNode.class)
@NodeChild(value = "increment", type = ValueNode.class)
public abstract class RangeIteration extends TransformNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private RangeRepeatingNode repeatingNode;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private LoopNode loop;

  private final boolean inclusiveStart;
  private final boolean inclusiveEnd;

  protected RangeIteration(boolean inclusiveStart, boolean inclusiveEnd) {
    this.inclusiveStart = inclusiveStart;
    this.inclusiveEnd = inclusiveEnd;
  }

  public void setStage(int rangeCvSlot, TransformNode stage) {
    repeatingNode = new RangeRepeatingNode(rangeCvSlot, stage, inclusiveStart, inclusiveEnd);
    loop = Truffle.getRuntime().createLoopNode(repeatingNode);
  }

  @Override
  public void setResultSlot(int resultSlot) {
    super.setResultSlot(resultSlot);
    repeatingNode.setResultSlot(resultSlot);
  }

  public static RangeIteration create(int rangeCvSlot, TransformNode stage, ValueNode start,
      boolean inclusiveStart, ValueNode end, ValueNode increment, boolean inclusiveEnd) {
    RangeIteration created = RangeIterationNodeGen.create(inclusiveStart, inclusiveEnd, start, end, increment);
    created.setStage(rangeCvSlot, stage);
    return created;
  }

  public static RangeIteration create(ValueNode start, boolean inclusiveStart, ValueNode end, boolean inclusiveEnd, ValueNode increment) {
    return RangeIterationNodeGen.create(inclusiveStart, inclusiveEnd, start, end, increment);
  }

  @Specialization
  public void doLong(VirtualFrame frame, long start, long end, long increment) {
    repeatingNode.initialize(start, end, increment);
    loop.execute(frame);
  }

  @Specialization
  public void doObject(Object start, Object end, Object increment) {
    throw new UnsupportedOperationException(String.format("No range iterator for %s %s %s", start.getClass().getName(), end.getClass().getName(), increment.getClass().getName()));
  }

  static class RangeRepeatingNode extends Node implements RepeatingNode {
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private RangeIteratorNode iterator;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private SetChainCvNode setCurrentValue;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    TransformNode stage;

    RangeRepeatingNode(int rangeCvSlot, TransformNode stage, boolean inclusiveStart,
        boolean inclusiveEnd) {
      iterator = RangeIteratorNode.create(inclusiveStart, inclusiveEnd);
      setCurrentValue = SetChainCvNode.create(rangeCvSlot);
      this.stage = stage;
    }

    void initialize(long start, long end, long increment) {
      this.iterator.initialize(start, end, increment);
    }

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
      try {
        setCurrentValue.execute(frame, iterator.execute(frame));
      } catch (EndOfStreamException e) {
        return false;
      }
      stage.executeTransform(frame);
      return true;
    }

    void setResultSlot(int resultSlot) {
      stage.setResultSlot(resultSlot);
    }
  }

  static abstract class RangeIteratorNode extends Node {
    long current;
    long end;
    long increment;

    final boolean inclusiveStart;
    final boolean inclusiveEnd;

    RangeIteratorNode(boolean inclusiveStart, boolean inclusiveEnd) {
      this.inclusiveStart = inclusiveStart;
      this.inclusiveEnd = inclusiveEnd;
    }

    public abstract Object execute(VirtualFrame frame);

    final CountingConditionProfile doneProfile = CountingConditionProfile.create();

    public void initialize(long start, long end, long increment) {
      current = start + (inclusiveStart ? 0 : increment);
      this.end = end;
      this.increment = increment;
    }

    @Specialization(guards = "increment > 0")
    long doIncreasingLong() {
      if (doneProfile.profile(current > end || (!inclusiveEnd && current == end))) {
        throw new EndOfStreamException();
      }
      long result = current;
      current += increment;
      return result;
    }

    @Specialization(guards = "increment < 0")
    long doDecreasingLong() {
      if (doneProfile.profile(current < end)) {
        throw new EndOfStreamException();
      }
      long result = current;
      current += increment;
      return result;
    }

    public static RangeIteratorNode create(boolean inclusiveStart, boolean inclusiveEnd) {
      return RangeIteratorNodeGen.create(inclusiveStart, inclusiveEnd);
    }
  }
}
