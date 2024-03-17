package tailspin.language.nodes.transform;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.Queue;
import tailspin.language.nodes.ExpressionNode;
import tailspin.language.nodes.StatementNode;

@NodeChild("resultExpr")
@NodeField(name = "emitSlot", type = int.class)
@ImportStatic(FrameSlotKind.class)
public class EmitNode extends StatementNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private ExpressionNode resultExpr;
  private final int emitSlot;

  public EmitNode(ExpressionNode resultExpr, int emitSlot) {
    this.resultExpr = resultExpr;
    this.emitSlot = emitSlot;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    @SuppressWarnings("unchecked")
    Queue<Object> results = (Queue<Object>) frame.getObjectStatic(emitSlot);
    results.add(resultExpr.executeGeneric(frame));
  }
}
