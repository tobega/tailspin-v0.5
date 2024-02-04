package tailspin.language.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public final class TailspinRootNode extends RootNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private ExpressionNode exprNode;

  public TailspinRootNode(ExpressionNode exprNode) {
    super(null);

    this.exprNode = exprNode;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    return this.exprNode.executeGeneric(frame);
  }
}