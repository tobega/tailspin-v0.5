package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.profiles.CountingConditionProfile;
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

  private MatchTemplateNode(MatcherNode matcher, StatementNode body) {
    this.matcher = matcher;
    this.body = body;
  }

  public boolean executeMatcher(VirtualFrame frame) {
    if (isTrue.profile(matcher.executeMatcher(frame))) {
      body.executeVoid(frame);
      return true;
    }
    return false;
  }

  public static MatchTemplateNode create(MatcherNode matcher, StatementNode body) {
    return new MatchTemplateNode(matcher, body);
  }
}
