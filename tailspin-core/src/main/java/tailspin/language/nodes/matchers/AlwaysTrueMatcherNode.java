package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;

public class AlwaysTrueMatcherNode extends MatcherNode {

  public AlwaysTrueMatcherNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  @Override
  @SuppressWarnings("unused")
  public boolean executeMatcherLong(VirtualFrame frame, long toMatch) {
    return true;
  }

  @Override
  @SuppressWarnings("unused")
  public boolean executeMatcherGeneric(VirtualFrame frame, Object toMatch) {
    return true;
  }

  public static AlwaysTrueMatcherNode create(SourceSection sourceSection) {
    return new AlwaysTrueMatcherNode(sourceSection);
  }
}
