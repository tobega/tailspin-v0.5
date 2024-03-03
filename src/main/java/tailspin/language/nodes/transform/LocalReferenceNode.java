package tailspin.language.nodes.transform;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.ExpressionNode;

@NodeField(name = "frameSlot", type = int.class)
public abstract class LocalReferenceNode extends ExpressionNode {

  protected abstract int getFrameSlot();

  @Specialization(guards = "frame.isLong(getFrameSlot())")
  protected long readLong(VirtualFrame frame) {
    return frame.getLong(this.getFrameSlot());
  }

  @Specialization(replaces = {"readLong"})
  protected Object readObject(VirtualFrame frame) {
    return frame.getObject(this.getFrameSlot());
  }
}

