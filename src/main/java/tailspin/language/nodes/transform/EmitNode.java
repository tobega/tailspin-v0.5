package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.EMIT_SLOT;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TransformNode;

public class EmitNode extends StatementNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private TransformNode resultExpr;

  private EmitNode(TransformNode resultExpr, SourceSection sourceSection) {
    super(sourceSection);
    this.resultExpr = resultExpr;
    resultExpr.setResultSlot(EMIT_SLOT);
  }

  public static EmitNode create(TransformNode resultExpr, SourceSection sourceSection) {
    return new EmitNode(resultExpr, sourceSection);
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    resultExpr.executeTransform(frame);
  }
}
