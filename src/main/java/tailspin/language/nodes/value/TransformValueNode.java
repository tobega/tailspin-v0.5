package tailspin.language.nodes.value;

import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.Iterator;
import tailspin.language.TypeError;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;

public class TransformValueNode extends ValueNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private TransformNode transformNode;

  public TransformValueNode(TransformNode transformNode) {
    this.transformNode = transformNode;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    Iterator<Object> results = transformNode.executeTransform(frame);
    Object value = results.next();
    if (results.hasNext()) {
      throw new TypeError("Got more than one value");
    }
    return value;
  }
}
