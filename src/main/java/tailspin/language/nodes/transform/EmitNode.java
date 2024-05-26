package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.EMIT_SLOT;

import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.ArrayList;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;

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
  @SuppressWarnings("unchecked")
  public void executeVoid(VirtualFrame frame) {
    Object previous = frame.getObjectStatic(EMIT_SLOT);
    Object result = resultExpr.executeGeneric(frame);
    if (previous == null) {
      frame.setObjectStatic(EMIT_SLOT, result);
    } else if (result != null) {
      if (previous instanceof ArrayList<?> values) {
        if (result instanceof ArrayList<?> results) {
          ((ArrayList<Object>) values).addAll(results);
        } else {
          ((ArrayList<Object>) values).add(result);
        }
      } else if (result instanceof ArrayList<?> results) {
        // TODO: this seems to be the hot spot
        ((ArrayList<Object>) results).addFirst(previous);
        frame.setObjectStatic(EMIT_SLOT, results);
      } else {
        ArrayList<Object> values = new ArrayList<>();
        values.add(previous);
        values.add(result);
        frame.setObjectStatic(EMIT_SLOT, values);
      }
    }
  }
}
