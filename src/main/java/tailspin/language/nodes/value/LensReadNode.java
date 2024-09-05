package tailspin.language.nodes.value;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import java.util.ArrayList;
import tailspin.language.TypeError;
import tailspin.language.nodes.LensProjectionNode;
import tailspin.language.runtime.TailspinArray;

public class LensReadNode extends Node {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  LensProjectionNode projectionNode;

  LensReadNode(LensProjectionNode projectionNode) {
    this.projectionNode = projectionNode;
  }

  public void executeRead(VirtualFrame frame, ArrayList<Object> target, ArrayList<Object> accumulator) {
    if (accumulator.size() > target.size()) {
      Object result = accumulator.removeLast();
      Object previous = accumulator.removeLast();
      accumulator.addLast(mergeResult(previous, result));
    }
    Object reference;
    if (target.getLast() == null) {
      target.removeLast();
      return;
    } else if (target.getLast() instanceof ArrayList<?> many) {
      if (many.isEmpty()) {
        target.removeLast();
        return;
      } else {
        reference = many.removeFirst();
      }
    } else {
      reference = target.removeLast();
      target.addLast(null);
    }
    Object values = projectionNode.executeProjection(frame, reference);
    target.addLast(values);
    if (values instanceof ArrayList<?> ignored) {
      accumulator.addLast(new ArrayList<>());
    } else {
      accumulator.addLast(null);
    }
  }

  @SuppressWarnings("unchecked")
  static Object mergeResult(Object previous, Object result) {
    if (result == null) {
      throw new TypeError("No such element");
    }
    if (result instanceof ArrayList<?> many) {
      result = TailspinArray.value(many.toArray());
    }
    if (previous instanceof ArrayList<?> many) {
      ((ArrayList<Object>)many).addLast(result);
      return many;
    } else if (previous == null) {
      return result;
    } else {
      throw new AssertionError("Lens result mismatch");
    }
  }

  public static LensReadNode create(LensProjectionNode projectionNode) {
    return new LensReadNode(projectionNode);
  }
}
