package tailspin.language.nodes.value;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;

public class TransformResultNode extends ValueNode {

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private TransformNode transform;

  public TransformResultNode(TransformNode transform) {
    this.transform = transform;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    transform.executeTransform(frame);
    Object result = frame.getObjectStatic(transform.getResultSlot());
    frame.setObjectStatic(transform.getResultSlot(), null);
    return result;
  }
}
