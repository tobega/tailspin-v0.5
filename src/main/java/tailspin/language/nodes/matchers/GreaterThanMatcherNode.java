package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import java.math.BigInteger;
import tailspin.language.TypeError;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;

@NodeChild(value = "toMatchNode", type = ValueNode.class)
@NodeChild(value = "valueNode", type = ValueNode.class)
public abstract class GreaterThanMatcherNode extends MatcherNode {

  private final boolean inclusive;

  protected GreaterThanMatcherNode(boolean inclusive) {
    this.inclusive = inclusive;
  }

  @Specialization
  protected boolean longMore(long toMatch, long value) {
    return toMatch > value || inclusive && toMatch == value;
  }

  @Specialization(replaces = "longMore")
  @TruffleBoundary
  protected boolean bigIntegerMore(BigInteger toMatch, BigInteger value) {
    return toMatch.compareTo(value) >= (inclusive ? 0 : 1);
  }

  @Specialization
  @SuppressWarnings("unused")
  protected boolean objectMore(Object toMatch, Object value) {
    throw new TypeError("Cannot order " + value.getClass());
  }

  public static GreaterThanMatcherNode create(boolean inclusive, ValueNode toMatchNode, ValueNode valueNode) {
    return GreaterThanMatcherNodeGen.create(inclusive, toMatchNode, valueNode);
  }
}
