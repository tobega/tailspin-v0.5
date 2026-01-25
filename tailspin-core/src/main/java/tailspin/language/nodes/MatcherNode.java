package tailspin.language.nodes;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;

@TypeSystemReference(TailspinTypes.class)
public abstract class MatcherNode extends TailspinNode {

  public MatcherNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  public abstract boolean executeMatcherGeneric(VirtualFrame frame, Object toMatch);
  public abstract boolean executeMatcherLong(VirtualFrame frame, long toMatch);
}
