package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.source.SourceSection;
import java.util.List;
import tailspin.language.nodes.MatcherNode;

public class AllOfNode extends MatcherNode {
  @Children
  private final MatcherNode[] conditions;

  private AllOfNode(List<MatcherNode> conditions, SourceSection sourceSection) {
    super(sourceSection);
    this.conditions = conditions.toArray(new MatcherNode[0]);
  }

  public static AllOfNode create(List<MatcherNode> conditions, SourceSection sourceSection) {
    return new AllOfNode(conditions, sourceSection);
  }

  @Override
  @ExplodeLoop
  public boolean executeMatcherLong(VirtualFrame frame, long toMatch) {
    for (MatcherNode m : conditions) {
      if (!m.executeMatcherLong(frame, toMatch)) return false;
    }
    return true;
  }

  @Override
  @ExplodeLoop
  public boolean executeMatcherGeneric(VirtualFrame frame, Object toMatch) {
    for (MatcherNode m : conditions) {
      if (!m.executeMatcherGeneric(frame, toMatch)) return false;
    }
    return true;
  }
}
