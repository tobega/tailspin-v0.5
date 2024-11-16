package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.profiles.CountingConditionProfile;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TailspinNode;

public class MatchTemplateNode extends TailspinNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private MatcherNode matcher;
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private StatementNode body;
  final CountingConditionProfile isTrue = CountingConditionProfile.create();

  private MatchTemplateNode(MatcherNode matcher, StatementNode body, SourceSection sourceSection) {
    super(sourceSection);
    this.matcher = matcher;
    this.body = body;
  }

  public boolean executeLong(VirtualFrame frame, long toMatch) {
    if (isTrue.profile(matcher.executeMatcherLong(frame, toMatch))) {
      body.executeVoid(frame);
      return true;
    }
    return false;
  }

  public boolean executeGeneric(VirtualFrame frame, Object toMatch) {
    if (isTrue.profile(matcher.executeMatcherGeneric(frame, toMatch))) {
      body.executeVoid(frame);
      return true;
    }
    return false;
  }

  public static MatchTemplateNode create(MatcherNode matcher, StatementNode body,
      SourceSection sourceSection) {
    return new MatchTemplateNode(matcher, body, sourceSection);
  }
}
