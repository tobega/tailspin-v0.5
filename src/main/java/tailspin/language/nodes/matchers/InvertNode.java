package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;

public class InvertNode extends MatcherNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private MatcherNode matcher;

  private InvertNode(MatcherNode matcher, SourceSection sourceSection) {
    super(sourceSection);
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

  public static InvertNode create(MatcherNode matcher, SourceSection sourceSection) {
    return new InvertNode(matcher, sourceSection);
  }
}
