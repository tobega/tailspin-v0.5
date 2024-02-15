package tailspin.language.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.Iterator;

public abstract class TransformNode extends TailspinNode {
  public abstract Iterator<Object> executeGeneric(VirtualFrame frame);
}
