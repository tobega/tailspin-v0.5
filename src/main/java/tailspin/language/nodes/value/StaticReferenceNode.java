package tailspin.language.nodes.value;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.ValueNode;

public class StaticReferenceNode extends ValueNode {

  private final int slot;

  private StaticReferenceNode(int slot) {
    this.slot = slot;
  }

  public static StaticReferenceNode create(int valuesSlot) {
    return new StaticReferenceNode(valuesSlot);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return frame.getObjectStatic(slot);
  }
}
