package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.MatcherNode;

public class AlwaysTrueMatcherNode extends MatcherNode {

  public AlwaysTrueMatcherNode() {
    super(null);
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

  public static AlwaysTrueMatcherNode create() {
    return new AlwaysTrueMatcherNode();
  }
}
