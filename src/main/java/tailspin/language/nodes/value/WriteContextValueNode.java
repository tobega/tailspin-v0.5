package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.GenerateCached;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Reference;

@NodeChild(value = "valueExpr", type = ValueNode.class)
@GenerateCached(alwaysInlineCached=true)
public abstract class WriteContextValueNode extends StatementNode {
  private final Reference reference;

  protected WriteContextValueNode(Reference reference) {
    this.reference = reference;
  }

  @Specialization
  protected void writeLong(VirtualFrame frame, long value,
      @Cached(neverDefault = true) @Shared GetContextFrameNode getFrame,
      @Cached(neverDefault = true) @Shared WriteLocalValueNode writeValue) {
    VirtualFrame contextFrame = getFrame.execute(frame, this, reference.getLevel());
    writeValue.executeLong(contextFrame, this, reference.getSlot(), value);
  }

  @Specialization
  protected void writeObject(VirtualFrame frame, Object value,
      @Cached(neverDefault = true) @Shared GetContextFrameNode getFrame,
      @Cached(neverDefault = true) @Shared WriteLocalValueNode writeValue) {
    VirtualFrame contextFrame = getFrame.execute(frame, this, reference.getLevel());
    writeValue.executeGeneric(contextFrame, this, reference.getSlot(), value);
  }

  public static WriteContextValueNode create(int level, int slot, ValueNode valueNode) {
    return create(Reference.to(level, slot), valueNode);
  }

  public static WriteContextValueNode create(Reference reference, ValueNode valueNode) {
    return WriteContextValueNodeGen.create(reference, valueNode);
  }

  @ImportStatic(FrameSlotKind.class)
  @GenerateInline
  public static abstract class WriteLocalValueNode extends Node {
    public abstract void executeLong(VirtualFrame frame, Node node, int slot, long value);
    public abstract void executeGeneric(VirtualFrame frame, Node node, int slot, Object value);

    @Specialization(guards = "frame.getFrameDescriptor().getSlotKind(slot) == Illegal || " +
        "frame.getFrameDescriptor().getSlotKind(slot) == Long")
    protected void writeLong(VirtualFrame frame, int slot, long value) {
      frame.getFrameDescriptor().setSlotKind(slot, FrameSlotKind.Long);
      frame.setLong(slot, value);
    }

    @Specialization(replaces = "writeLong")
    protected void writeObject(VirtualFrame frame, int slot, Object value) {
      frame.getFrameDescriptor().setSlotKind(slot, FrameSlotKind.Object);
      frame.setObject(slot, value);
    }
  }

}
