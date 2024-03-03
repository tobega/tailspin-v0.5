package tailspin.language.nodes.transform;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.runtime.DeepIterator;

public class ChainStageNode extends TransformNode {

  private final int resultSlot;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private LoopNode loop;

  public ChainStageNode(StatementNode setCurrentValue, TransformNode stage, int resultSlot) {
    this.resultSlot = resultSlot;
    loop = Truffle.getRuntime().createLoopNode(new ChainStageRepeatingNode(setCurrentValue, stage, resultSlot));
  }

  @Override
  public Iterator<Object> execute(VirtualFrame frame) {
    Queue<Object> results = new ArrayDeque<>();
    frame.setObject(resultSlot, results);
    loop.execute(frame);
    return new DeepIterator(results.iterator());
  }

  private static class ChainStageRepeatingNode extends Node implements RepeatingNode {
    int resultSlot;
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private StatementNode setCurrentValue;
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    TransformNode stage;

    ChainStageRepeatingNode(StatementNode setCurrentValue, TransformNode stage, int resultSlot) {
      this.setCurrentValue = setCurrentValue;
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
      @SuppressWarnings("unchecked")
      Queue<Object> results = (Queue<Object>) frame.getObject(resultSlot);
      results.add(stage.execute(frame));
      return true;
    }
  }
}
