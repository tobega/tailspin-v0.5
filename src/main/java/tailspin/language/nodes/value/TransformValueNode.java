package tailspin.language.nodes.value;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.TypeError;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.ResultIterator;

public class TransformValueNode extends ValueNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private TransformNode transformNode;

  public TransformValueNode(TransformNode transformNode) {
    this.transformNode = transformNode;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    ResultIterator results = transformNode.executeTransform(frame);
    Object value = results.getIteratorNextElement();
    if (results.hasIteratorNextElement()) {
      throw new TypeError("Got more than one value");
    }
    return value;
  }
}
