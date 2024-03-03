package tailspin.language.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class StatementNode extends TailspinNode {
  public abstract void executeVoid(VirtualFrame frame);
}
