package tailspin.language;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import tailspin.language.nodes.ExpressionNode;
import tailspin.language.nodes.TransformNode;

public class TestUtil {
  public static Object evaluate(ExpressionNode node, Object... arguments) {
    var rootNode = new TestRootNode(node);
    CallTarget callTarget = rootNode.getCallTarget();

    return callTarget.call(arguments);
  }

  public static Object run(TransformNode node, Object... arguments) {
    var rootNode = new TransformRootNode(node);
    CallTarget callTarget = rootNode.getCallTarget();

    return callTarget.call(arguments);
  }

  public static final class TestRootNode extends RootNode {
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private ExpressionNode exprNode;

    public TestRootNode(ExpressionNode exprNode) {
      super(null);

      this.exprNode = exprNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
      return this.exprNode.executeGeneric(frame);
    }
  }

  public static final class TransformRootNode extends RootNode {
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private TransformNode node;

    public TransformRootNode(TransformNode node) {
      super(null);

      this.node = node;
    }

    @Override
    public Object execute(VirtualFrame frame) {
      this.node.executeGeneric(frame);
      return frame.getArguments()[1];
    }
  }
}
