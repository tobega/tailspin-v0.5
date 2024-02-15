package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.Iterator;
import tailspin.language.nodes.ExpressionNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.runtime.OneResultValue;

public class ExpressionTransformNode extends TransformNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private ExpressionNode expression;

  public ExpressionTransformNode(ExpressionNode expression) {
    this.expression = expression;
  }

  public Iterator<Object> executeGeneric(VirtualFrame frame) {
    return new OneResultValue(expression.executeGeneric(frame));
  }

}
