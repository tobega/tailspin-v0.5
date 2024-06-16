package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateCached;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import tailspin.language.nodes.TailspinTypesGen;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.ReadContextValueNodeGen.ReadLocalValueNodeGen;

@NodeField(name = "level", type = int.class)
@NodeField(name = "slot", type = int.class)
@GenerateCached(alwaysInlineCached=true)
public abstract class ReadContextValueNode extends ValueNode {
  protected abstract int getLevel();
  protected abstract int getSlot();

  @Specialization
  protected Object readObject(VirtualFrame frame,
      @Cached(neverDefault = true) GetContextFrameNode getFrame,
      @Cached(neverDefault = true) ReadLocalValueNode readValue) {
    VirtualFrame contextFrame = getFrame.execute(frame, this, getLevel());
    return readValue.executeGeneric(contextFrame, this, getSlot());
  }

  public static ReadContextValueNode create(int level, int slot) {
    return ReadContextValueNodeGen.create(level, slot);
  }

  @GenerateInline
  public abstract static class ReadLocalValueNode extends Node {
    public long executeLong(VirtualFrame frame, Node node, int slot) throws UnexpectedResultException {
      return TailspinTypesGen.expectLong(executeGeneric(frame, node, slot));
    }
    public abstract Object executeGeneric(VirtualFrame frame, Node node, int slot);

    @Specialization(guards = "frame.isLong(slot)")
    protected long readLong(VirtualFrame frame, int slot) {
      return frame.getLong(slot);
    }

    @Specialization(replaces = {"readLong"})
    protected Object readObject(VirtualFrame frame, int slot) {
      return frame.getObject(slot);
    }

    public static ReadLocalValueNode create() {
      return ReadLocalValueNodeGen.create();
    }
  }
}

