package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateCached;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import tailspin.language.nodes.TailspinTypesGen;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.ReadContextValueNodeGen.ReadLocalValueNodeGen;
import tailspin.language.runtime.Reference;

@GenerateCached(alwaysInlineCached=true)
public abstract class ReadContextValueNode extends ValueNode {
  private final Reference reference;

  protected ReadContextValueNode(Reference reference) {
    this.reference = reference;
  }

  @Specialization
  protected Object readObject(VirtualFrame frame,
      @Cached(neverDefault = true) GetContextFrameNode getFrame,
      @Cached(neverDefault = true) ReadLocalValueNode readValue) {
    VirtualFrame contextFrame = getFrame.execute(frame, this, reference.getLevel());
    return readValue.executeGeneric(contextFrame, this, reference.getSlot());
  }

  public static ReadContextValueNode create(int level, int slot) {
    return create(Reference.to(level, slot));
  }
  public static ReadContextValueNode create(Reference reference) {
    return ReadContextValueNodeGen.create(reference);
  }

  @GenerateInline
  public abstract static class ReadLocalValueNode extends Node {
    public long executeLong(VirtualFrame frame, Node node, int slot) throws UnexpectedResultException {
      return TailspinTypesGen.expectLong(executeGeneric(frame, node, slot));
    }
    public abstract Object executeGeneric(VirtualFrame frame, Node node, int slot);

    @Specialization
    protected Object readObject(VirtualFrame frame, int slot) {
      return frame.getObjectStatic(slot);
    }

    public static ReadLocalValueNode create() {
      return ReadLocalValueNodeGen.create();
    }
  }
}

