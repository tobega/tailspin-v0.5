package tailspin.language.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import java.util.Iterator;

public abstract class TransformNode extends Node {
  public abstract Iterator<Object> executeTransform(VirtualFrame frame);
}
