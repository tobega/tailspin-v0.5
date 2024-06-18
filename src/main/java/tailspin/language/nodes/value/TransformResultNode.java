package tailspin.language.nodes.value;

import static tailspin.language.runtime.Templates.TEMP_RESULT_SLOT;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;

public class TransformResultNode extends ValueNode {

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private TransformNode transform;

  public TransformResultNode(TransformNode transform) {
    this.transform = transform;
    transform.setResultSlot(TEMP_RESULT_SLOT);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    transform.executeTransform(frame);
    Object result = frame.getObjectStatic(TEMP_RESULT_SLOT);
    frame.setObjectStatic(TEMP_RESULT_SLOT, null);
    return result;
  }
}
