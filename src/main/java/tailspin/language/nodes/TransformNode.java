package tailspin.language.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.runtime.ResultIterator;

public abstract class TransformNode extends Node {
  public abstract ResultIterator executeTransform(VirtualFrame frame);
}
