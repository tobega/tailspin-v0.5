package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.GenerateCached;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.ValueNode;

@NodeField(name = "level", type = int.class)
@NodeField(name = "slot", type = int.class)
@GenerateCached(alwaysInlineCached=true)
public abstract class ReadContextValueNode extends ValueNode {
  protected abstract int getLevel();
  protected abstract int getSlot();

  @Specialization(guards = "frame.isLong(getSlot())")
  protected long readLong(VirtualFrame frame,
      @Shared("get") @Cached(neverDefault = true) GetContextFrameNode getFrame,
      @Shared("read") @Cached(neverDefault = true) ReadLocalValueNode readValue) {
    VirtualFrame contextFrame = getFrame.execute(frame, this, getLevel());
    return readValue.readLong(contextFrame, getSlot());
  }

  @Specialization(replaces = {"readLong"})
  protected Object readObject(VirtualFrame frame,
      @Shared("get") @Cached(neverDefault = true) GetContextFrameNode getFrame,
      @Shared("read") @Cached(neverDefault = true) ReadLocalValueNode readValue) {
    VirtualFrame contextFrame = getFrame.execute(frame, this, getLevel());
    return readValue.readObject(contextFrame, getSlot());
  }

  public static ReadContextValueNode create(int level, int slot) {
    return ReadContextValueNodeGen.create(level, slot);
  }
}

