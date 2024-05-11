package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.EMIT_SLOT;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.ResultIterator;

public class EmitNode extends StatementNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private ValueNode resultExpr;

  private EmitNode(ValueNode resultExpr) {
    this.resultExpr = resultExpr;
  }

  public static EmitNode create(ValueNode resultExpr) {
    return new EmitNode(resultExpr);
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    Object previous = frame.getObjectStatic(EMIT_SLOT);
    Object result = resultExpr.executeGeneric(frame);
    frame.setObjectStatic(EMIT_SLOT, ResultIterator.merge(previous, result));
  }
}
