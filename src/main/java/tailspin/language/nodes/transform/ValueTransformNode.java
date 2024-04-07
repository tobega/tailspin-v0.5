package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.Iterator;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.OneValueIterator;

public class ValueTransformNode extends TransformNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private ValueNode valueNode;

  public ValueTransformNode(ValueNode valueNode) {
    this.valueNode = valueNode;
  }

  @Override
  public Iterator<Object> executeTransform(VirtualFrame frame) {
    return new OneValueIterator(valueNode.executeGeneric(frame));
  }
}
