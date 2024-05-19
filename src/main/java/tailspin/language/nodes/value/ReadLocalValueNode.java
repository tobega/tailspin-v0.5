package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import tailspin.language.nodes.TailspinTypesGen;

@GenerateInline(false)
public abstract class ReadLocalValueNode extends Node {
  public long executeLong(VirtualFrame frame, int slot) throws UnexpectedResultException {
    return TailspinTypesGen.expectLong(executeGeneric(frame, slot));
  }
  public abstract Object executeGeneric(VirtualFrame frame, int slot);

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

