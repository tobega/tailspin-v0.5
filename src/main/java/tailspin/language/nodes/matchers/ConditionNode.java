package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;

@NodeChild(value = "dummy", type = ValueNode.class)
@NodeChild(value = "toMatchNode", type = ValueNode.class)
@NodeChild(value = "matcherNode", type = MatcherNode.class, executeWith = "toMatchNode")
public abstract class ConditionNode extends MatcherNode {
  @Specialization
  @SuppressWarnings("unused")
  boolean doGeneric(Object dummyValue, Object toMatch, boolean result) {
    return result;
  }

  public static ConditionNode create(ValueNode toMatchNode, MatcherNode matcherNode) {
    return ConditionNodeGen.create(null, toMatchNode, matcherNode);
  }
}
