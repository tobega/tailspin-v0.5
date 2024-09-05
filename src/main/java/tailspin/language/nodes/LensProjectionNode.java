package tailspin.language.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class LensProjectionNode extends ValueNode {
  public abstract Object executeProjection(VirtualFrame frame, Object target);

  // TODO: temporary for state mutate functionality
  public abstract LensProjectionNode withTarget(ValueNode target);

}
