package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.ResultIterator;

public class ValueTransformNode extends TransformNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private ValueNode valueNode;

  public ValueTransformNode(ValueNode valueNode) {
    this.valueNode = valueNode;
  }

  @Override
  public ResultIterator executeTransform(VirtualFrame frame) {
    Object value = valueNode.executeGeneric(frame);
    return ResultIterator.of(new Object[]{value});
  }
}
