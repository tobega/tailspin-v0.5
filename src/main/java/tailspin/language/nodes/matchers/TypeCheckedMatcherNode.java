package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.TypeError;
import tailspin.language.nodes.MatcherNode;

public class TypeCheckedMatcherNode extends MatcherNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  protected MatcherNode typeCheck;

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  protected MatcherNode matcher;

  public TypeCheckedMatcherNode(MatcherNode typeCheck, MatcherNode matcher) {
    this.typeCheck = typeCheck;
    this.matcher = matcher;
  }

  @Override
  public boolean executeMatcherGeneric(VirtualFrame frame, Object toMatch) {
    if (!typeCheck.executeMatcherGeneric(frame, toMatch)) {
      throw new TypeError(toMatch + " not in type bound");
    }
    return matcher.executeMatcherGeneric(frame, toMatch);
  }

  @Override
  public boolean executeMatcherLong(VirtualFrame frame, long toMatch) {
    if (!typeCheck.executeMatcherLong(frame, toMatch)) {
      throw new TypeError(toMatch + " not in type bound");
    }
    return matcher.executeMatcherLong(frame, toMatch);
  }

  public static TypeCheckedMatcherNode create(MatcherNode typeCheck, MatcherNode matcher) {
    return new TypeCheckedMatcherNode(typeCheck, matcher);
  }
}
