package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.ValueNode;

public class ReadCvArgumentNode extends ValueNode {
  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return frame.getArguments()[0];
  }
}
