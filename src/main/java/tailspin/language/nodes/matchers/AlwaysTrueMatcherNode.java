package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.MatcherNode;

public class AlwaysTrueMatcherNode extends MatcherNode {

  @Override
  public boolean executeMatcher(VirtualFrame frame) {
    return true;
  }

  public static AlwaysTrueMatcherNode create() {
    return new AlwaysTrueMatcherNode();
  }
}
