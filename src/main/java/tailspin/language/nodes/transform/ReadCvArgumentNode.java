package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.ExpressionNode;

public class ReadCvArgumentNode extends ExpressionNode {
  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return frame.getArguments()[0];
  }
}
