package tailspin.language.nodes.value;

import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.ArrayList;
import java.util.List;
import tailspin.language.nodes.LensProjectionNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinArray;

public class LensReadExpressionNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  ValueNode referenceNode;

  @Children
  final LensReadNode[] readLenses;

  public LensReadExpressionNode(ValueNode referenceNode, LensReadNode[] readLenses) {
    this.referenceNode = referenceNode;
    this.readLenses = readLenses;
  }

  public static LensReadExpressionNode create(ValueNode referenceNode, List<LensProjectionNode> readLenses) {
    return new LensReadExpressionNode(referenceNode,
        readLenses.stream().map(LensReadNode::create).toArray(LensReadNode[]::new));
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    ArrayList<Object> accumulator = new ArrayList<>();
    accumulator.add(null);
    ArrayList<Object> target = new ArrayList<>();
    target.add(referenceNode.executeGeneric(frame));
    int lensIndex = 0;
    while (!target.isEmpty()) {
      while (lensIndex < readLenses.length) {
        readLenses[lensIndex].executeRead(frame, target, accumulator);
        lensIndex++;
      }
      Object result = target.removeLast();
      if (result instanceof ArrayList<?> many) {
        result = TailspinArray.value(many.toArray());
      }
      accumulator.removeLast();
      accumulator.addLast(result);
      while (lensIndex > 0 && accumulator.size() > target.size()) {
        lensIndex--;
        readLenses[lensIndex].executeRead(frame, target, accumulator);
      }
      lensIndex++;
    }
    return accumulator.removeLast();
  }
}
