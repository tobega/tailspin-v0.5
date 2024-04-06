package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.Queue;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TransformNode;

public class EmitNode extends StatementNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private TransformNode resultExpr;
  private final int emitSlot;

  public EmitNode(TransformNode resultExpr, int emitSlot) {
    this.resultExpr = resultExpr;
    this.emitSlot = emitSlot;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    @SuppressWarnings("unchecked")
    Queue<Object> results = (Queue<Object>) frame.getObjectStatic(emitSlot);
    results.add(resultExpr.executeTransform(frame));
  }
}
