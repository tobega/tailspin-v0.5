package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.MatcherNode;

public class InvertNode extends MatcherNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private MatcherNode matcher;

  private InvertNode(MatcherNode matcher) {
    this.matcher = matcher;
  }


  @Override
  public boolean executeMatcherGeneric(VirtualFrame frame, Object toMatch) {
    return !matcher.executeMatcherGeneric(frame, toMatch);
  }

  @Override
  public boolean executeMatcherLong(VirtualFrame frame, long toMatch) {
    return !matcher.executeMatcherLong(frame, toMatch);
  }

  public static InvertNode create(MatcherNode matcher) {
    return new InvertNode(matcher);
  }
}
