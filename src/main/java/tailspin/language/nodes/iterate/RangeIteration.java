package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import java.util.ArrayList;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.ChainStageNode.SetChainCvNode;
import tailspin.language.nodes.iterate.RangeIterationNodeGen.RangeIteratorNodeGen;
import tailspin.language.nodes.transform.EndOfStreamException;

@NodeChild(value = "start", type = ValueNode.class)
@NodeChild(value = "end", type = ValueNode.class)
@NodeChild(value = "increment", type = ValueNode.class)
public abstract class RangeIteration extends ValueNode {
  final int resultSlot;

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private RangeRepeatingNode repeatingNode;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private LoopNode loop;

  protected RangeIteration(int rangeCvSlot, ValueNode stage, int resultSlot) {
    this.resultSlot = resultSlot;
    repeatingNode = new RangeRepeatingNode(rangeCvSlot, stage, resultSlot);
    loop = Truffle.getRuntime().createLoopNode(repeatingNode);
  }

  public static RangeIteration create(int rangeCvSlot, ValueNode stage, int resultSlot, ValueNode start, ValueNode end, ValueNode increment) {
    return RangeIterationNodeGen.create(rangeCvSlot, stage, resultSlot, start, end, increment);
  }

  @Specialization
  public Object doLong(VirtualFrame frame, long start, long end, long increment) {
    frame.setObjectStatic(resultSlot, new ArrayList<>());
    repeatingNode.initialize(start, end, increment);
    loop.execute(frame);
    Object results = frame.getObjectStatic(resultSlot);
    frame.setObjectStatic(resultSlot, null);
    return results;
  }

  @Specialization
  public Object doObject(Object start, Object end, Object increment) {
    throw new UnsupportedOperationException(String.format("No range iterator for %s %s %s", start.getClass().getName(), end.getClass().getName(), increment.getClass().getName()));
  }

  public static class RangeRepeatingNode extends Node implements RepeatingNode {
    final int resultSlot;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private RangeIteratorNode iterator;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private SetChainCvNode setCurrentValue;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    ValueNode stage;

    public RangeRepeatingNode(int rangeCvSlot, ValueNode stage, int resultSlot) {
      this.resultSlot = resultSlot;
      iterator = RangeIteratorNode.create();
      setCurrentValue = SetChainCvNode.create(rangeCvSlot);
      this.stage = stage;
    }

    public void initialize(long start, long end, long increment) {
      this.iterator.initialize(start, end, increment);
    }

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
      try {
        setCurrentValue.execute(frame, iterator.execute(frame));
      } catch (EndOfStreamException e) {
        return false;
      }
      Object result = stage.executeGeneric(frame);
      if (result != null) {
        @SuppressWarnings("unchecked")
        ArrayList<Object> previous = (ArrayList<Object>) frame.getObjectStatic(resultSlot);
        if (result instanceof ArrayList<?> values) {
          previous.addAll(values);
        } else {
          previous.add(result);
        }
      }
      return true;
    }
  }

  static abstract class RangeIteratorNode extends Node {
    long current;
    long end;
    long increment;

    public abstract Object execute(VirtualFrame frame);

    public void initialize(long start, long end, long increment) {
      current = start;
      this.end = end;
      this.increment = increment;
    }

    @Specialization(guards = "increment > 0")
    long doIncreasingLong() {
      if (current > end) {
        throw new EndOfStreamException();
      }
      long result = current;
      current += increment;
      return result;
    }

    @Specialization(guards = "increment < 0")
    long doDecreasingLong() {
      if (current < end) {
        throw new EndOfStreamException();
      }
      long result = current;
      current += increment;
      return result;
    }

    public static RangeIteratorNode create() {
      return RangeIteratorNodeGen.create();
    }
  }
}
