package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.ArrayList;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;

public class ResultAggregatingNode extends TransformNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private ValueNode resultNode;

  ResultAggregatingNode(ValueNode resultNode) {
    this.resultNode = resultNode;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void executeTransform(VirtualFrame frame) {
    Object previous = frame.getObjectStatic(getResultSlot());
    Object result = resultNode.executeGeneric(frame);
    if (previous == null) {
      frame.setObjectStatic(getResultSlot(), result);
    } else if (result != null) {
      if (previous instanceof ArrayList<?> values) {
        if (result instanceof ArrayList<?> results) {
          ((ArrayList<Object>) values).addAll(results);
        } else {
          ((ArrayList<Object>) values).add(result);
        }
      } else if (result instanceof ArrayList<?> results) {
        ((ArrayList<Object>) results).addFirst(previous);
        frame.setObjectStatic(getResultSlot(), results);
      } else {
        ArrayList<Object> values = new ArrayList<>();
        values.add(previous);
        values.add(result);
        frame.setObjectStatic(getResultSlot(), values);
      }
    }
  }

  public static ResultAggregatingNode create(ValueNode resultNode) {
    return new ResultAggregatingNode(resultNode);
  }
}
