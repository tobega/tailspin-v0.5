package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;

public class ValueTransformNode extends TransformNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private ValueNode valueNode;

  public ValueTransformNode(ValueNode valueNode) {
    this.valueNode = valueNode;
  }

  @Override
  public Object executeTransform(VirtualFrame frame) {
    return valueNode.executeGeneric(frame);
  }
}
