package tailspin.language.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class MatcherNode extends TailspinNode {
  public abstract boolean executeMatcher(VirtualFrame frame);
}
