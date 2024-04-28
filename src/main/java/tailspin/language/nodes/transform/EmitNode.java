package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.ResultIterator;

public class EmitNode extends StatementNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private ValueNode resultExpr;
  private final int emitSlot;

  private EmitNode(ValueNode resultExpr, int emitSlot) {
    this.resultExpr = resultExpr;
    this.emitSlot = emitSlot;
  }

  public static EmitNode create(ValueNode resultExpr, int emitSlot) {
    return new EmitNode(resultExpr, emitSlot);
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    Object previous = frame.getObjectStatic(emitSlot);
    Object result = resultExpr.executeGeneric(frame);
    frame.setObjectStatic(emitSlot, ResultIterator.merge(previous, result));
  }
}
