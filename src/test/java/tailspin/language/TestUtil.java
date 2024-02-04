package tailspin.language;

import com.oracle.truffle.api.CallTarget;
import tailspin.language.nodes.ExpressionNode;
import tailspin.language.nodes.TailspinRootNode;

public class TestUtil {
  public static Object evaluate(ExpressionNode node) {
    var rootNode = new TailspinRootNode(node);
    CallTarget callTarget = rootNode.getCallTarget();

    return callTarget.call();
  }
}
