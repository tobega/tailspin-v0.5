package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import java.util.List;
import tailspin.language.nodes.MatcherNode;

public class AnyOfNode extends MatcherNode {
  @Children
  private final MatcherNode[] conditions;

  private AnyOfNode(List<MatcherNode> conditions) {
    this.conditions = conditions.toArray(new MatcherNode[0]);
  }

  public static AnyOfNode create(List<MatcherNode> conditions) {
    return new AnyOfNode(conditions);
  }

  @Override
  @ExplodeLoop
  public boolean executeMatcherLong(VirtualFrame frame, long toMatch) {
    for (MatcherNode m : conditions) {
      if (m.executeMatcherLong(frame, toMatch)) return true;
    }
    return false;
  }

  @Override
  @ExplodeLoop
  public boolean executeMatcherGeneric(VirtualFrame frame, Object toMatch) {
    for (MatcherNode m : conditions) {
      if (m.executeMatcherGeneric(frame, toMatch)) return true;
    }
    return false;
  }
}
