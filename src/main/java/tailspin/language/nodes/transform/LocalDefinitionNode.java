package tailspin.language.nodes.transform;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.ExpressionNode;

@NodeChild("valueExpr")
@NodeField(name = "frameSlot", type = int.class)
@ImportStatic(FrameSlotKind.class)
public abstract class LocalDefinitionNode extends ExpressionNode {
  protected abstract int getFrameSlot();

  @Specialization(guards = "frame.getFrameDescriptor().getSlotKind(getFrameSlot()) == Illegal || " +
      "frame.getFrameDescriptor().getSlotKind(getFrameSlot()) == Long")
  protected long longAssignment(VirtualFrame frame, long value) {
    int frameSlot = this.getFrameSlot();
    frame.getFrameDescriptor().setSlotKind(frameSlot, FrameSlotKind.Long);
    frame.setLong(frameSlot, value);
    return value;
  }

  @Specialization(replaces = "longAssignment")
  protected void objectAssignment(VirtualFrame frame, Object value) {
    var frameSlot = this.getFrameSlot();
    frame.getFrameDescriptor().setSlotKind(frameSlot, FrameSlotKind.Object);
    frame.setObject(frameSlot, value);
  }
}
