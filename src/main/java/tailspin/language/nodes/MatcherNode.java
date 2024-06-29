package tailspin.language.nodes;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;

@TypeSystemReference(TailspinTypes.class)
public abstract class MatcherNode extends TailspinNode {
  public abstract boolean executeMatcherLong(VirtualFrame frame, long toMatch);
  public abstract boolean executeMatcherGeneric(VirtualFrame frame, Object toMatch);
}
