package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.transform.AppendResultNode;

public class ResultAggregatingNode extends TransformNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private ValueNode resultNode;

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private AppendResultNode appendResultNode = AppendResultNode.create();

  ResultAggregatingNode(ValueNode resultNode) {
    this.resultNode = resultNode;
  }

  @Override
  public void executeTransform(VirtualFrame frame) {
    Object result = resultNode.executeGeneric(frame);
    appendResultNode.execute(frame, getResultSlot(), result);
  }

  public static ResultAggregatingNode create(ValueNode resultNode) {
    return new ResultAggregatingNode(resultNode);
  }
}
