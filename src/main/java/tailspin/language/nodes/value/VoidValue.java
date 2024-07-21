package tailspin.language.nodes.value;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.ValueNode;

public class VoidValue extends ValueNode {
  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }

  public static VoidValue create() {
    return new VoidValue();
  }
}
