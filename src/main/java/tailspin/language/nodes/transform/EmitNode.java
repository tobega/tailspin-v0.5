package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.EMIT_SLOT;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TransformNode;

public class EmitNode extends StatementNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private TransformNode resultExpr;

  private EmitNode(TransformNode resultExpr) {
    this.resultExpr = resultExpr;
    resultExpr.setResultSlot(EMIT_SLOT);
  }

  public static EmitNode create(TransformNode resultExpr) {
    return new EmitNode(resultExpr);
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    resultExpr.executeTransform(frame);
  }
}
