package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import java.util.Iterator;
import java.util.NoSuchElementException;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.ChainStageNode.SetChainCvNode;
import tailspin.language.runtime.ResultIterator;

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
    Iterator<Long> iterator = increment > 0
        ? new IncreasingIntegerRangeIterator(start, end,increment)
        : new DecreasingIntegerRangeIterator(start, end, -increment);
    repeatingNode.setIterator(iterator);
    loop.execute(frame);
    Object results = frame.getObjectStatic(resultSlot);
    frame.setObjectStatic(resultSlot, null);
    return results;
  }

  @Fallback
  public Object doObject(Object start, Object end, Object increment) {
    throw new UnsupportedOperationException(String.format("No range iterator for %s %s %s", start.getClass().getName(), end.getClass().getName(), increment.getClass().getName()));
  }

  public static class RangeRepeatingNode extends Node implements RepeatingNode {
    private Iterator<?> iterator;
    final int resultSlot;
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private SetChainCvNode setCurrentValue;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    ValueNode stage;

    public RangeRepeatingNode(int rangeCvSlot, ValueNode stage, int resultSlot) {
      this.resultSlot = resultSlot;
      setCurrentValue = SetChainCvNode.create(rangeCvSlot);
      this.stage = stage;
    }

    public void setIterator(Iterator<?> iterator) {
      this.iterator = iterator;
    }

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
      if (!iterator.hasNext()) return false;
      setCurrentValue.execute(frame, iterator.next());
      Object result = stage.executeGeneric(frame);
      Object previous = frame.getObjectStatic(resultSlot);
      frame.setObjectStatic(resultSlot, ResultIterator.merge(previous, result));
      return iterator.hasNext();
    }
  }

  public static class DecreasingIntegerRangeIterator implements Iterator<Long> {
    private final long end;
    private final long decrement;
    private long current;

    public DecreasingIntegerRangeIterator(long start, long end, long decrement) {
      this.end = end;
      if (decrement < 0) throw new AssertionError("Negative decrement");
      this.decrement = decrement;
      current = start;
    }

    @Override
    public boolean hasNext() {
      return current >= end;
    }

    @Override
    public Long next() {
      if (!hasNext()) throw new NoSuchElementException();
      Long result = current;
      current -= decrement;
      return result;
    }
  }

  public static class IncreasingIntegerRangeIterator implements Iterator<Long> {
    private final long end;
    private final long increment;
    private long current;

    public IncreasingIntegerRangeIterator(long start, long end, long increment) {
      this.end = end;
      this.increment = increment;
      current = start;
    }

    @Override
    public boolean hasNext() {
      return current <= end;
    }

    @Override
    public Long next() {
      if (!hasNext()) throw new NoSuchElementException();
      Long result = current;
      current += increment;
      return result;
    }
  }
}
