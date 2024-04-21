package tailspin.language.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;

public abstract class TransformNode extends Node {
  public abstract Object executeTransform(VirtualFrame frame);
}
