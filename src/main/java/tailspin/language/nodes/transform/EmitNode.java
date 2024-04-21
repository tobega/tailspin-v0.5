package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TransformNode;

public class EmitNode extends StatementNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private MergeResultNode mergedExpr;
  private final int emitSlot;

  public EmitNode(TransformNode resultExpr, int emitSlot) {
    this.mergedExpr = MergeResultNode.create(emitSlot, resultExpr);
    this.emitSlot = emitSlot;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    frame.setObjectStatic(emitSlot, mergedExpr.executeTransform(frame));
  }
}
