package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.Queue;
import tailspin.language.nodes.ExpressionNode;
import tailspin.language.nodes.TransformNode;

public class ExpressionTransformNode extends TransformNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private ExpressionNode expression;

  public ExpressionTransformNode(ExpressionNode expression) {
    this.expression = expression;
  }

  public void executeGeneric(VirtualFrame frame) {
    @SuppressWarnings("unchecked")
    Queue<Object> results = (Queue<Object>) frame.getArguments()[1];
    results.add(expression.executeGeneric(frame));
  }

}
