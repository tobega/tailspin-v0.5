package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.ValueNode;

@NodeField(name = "frameSlot", type = int.class)
public abstract class LocalReferenceNode extends ValueNode {

  protected abstract int getFrameSlot();

  @Specialization(guards = "frame.isLong(getFrameSlot())")
  protected long readLong(VirtualFrame frame) {
    return frame.getLong(this.getFrameSlot());
  }

  @Specialization(replaces = {"readLong"})
  protected Object readObject(VirtualFrame frame) {
    return frame.getObject(this.getFrameSlot());
  }

  public static LocalReferenceNode create(int frameSlot) {
    return LocalReferenceNodeGen.create(frameSlot);
  }
}

