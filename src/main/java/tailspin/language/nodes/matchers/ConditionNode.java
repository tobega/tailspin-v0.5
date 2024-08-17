package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;

public abstract class ConditionNode extends MatcherNode {
  private final int cvSlot;

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  ValueNode toMatchNode;

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  MatcherNode matcherNode;

  protected ConditionNode(int cvSlot, ValueNode toMatchNode, MatcherNode matcherNode) {
    super(null);
    this.cvSlot = cvSlot;
    this.toMatchNode = toMatchNode;
    this.matcherNode = matcherNode;
  }

  @Specialization
  @SuppressWarnings("unused")
  boolean doGeneric(VirtualFrame frame, Object outerToMatch,
      @Cached(inline = true) WriteLocalValueNode writeCv) {
    writeCv.executeGeneric(frame, this, cvSlot, outerToMatch);
    Object toMatch = toMatchNode.executeGeneric(frame);
    return matcherNode.executeMatcherGeneric(frame, toMatch);
  }

  public static ConditionNode create(int cvSlot, ValueNode toMatchNode, MatcherNode matcherNode) {
    return ConditionNodeGen.create(cvSlot, toMatchNode, matcherNode);
  }
}
