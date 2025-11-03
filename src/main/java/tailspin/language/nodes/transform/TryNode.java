package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.PreconditionFailed;
import tailspin.language.nodes.TransformNode;

public class TryNode  extends TransformNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private TransformNode transformNode;

  public TryNode(TransformNode transformNode, SourceSection sourceSection) {
    super(sourceSection);
    this.transformNode = transformNode;
  }

  @Override
  public void executeTransform(VirtualFrame frame) {
    try {
      transformNode.executeTransform(frame);
    } catch (PreconditionFailed pf) {
      // frame.setObjectStatic(getResultSlot(), new ArrayList<>());
    }
  }

  public static TryNode create(TransformNode transformNode, SourceSection sourceSection) {
    return new TryNode(transformNode, sourceSection);
  }

  public int getResultSlot() {
    return transformNode.getResultSlot();
  }

  public void setResultSlot(int resultSlot) {
    transformNode.setResultSlot(resultSlot);
  }
}
