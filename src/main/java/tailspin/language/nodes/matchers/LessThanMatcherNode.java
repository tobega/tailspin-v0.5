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
public abstract class LessThanMatcherNode extends MatcherNode {

  private final boolean inclusive;

  protected LessThanMatcherNode(boolean inclusive) {
    this.inclusive = inclusive;
  }

  @Specialization
  protected boolean longLess(long toMatch, long value) {
    return toMatch < value || inclusive && toMatch == value;
  }

  @Specialization(replaces = "longLess")
  @TruffleBoundary
  protected boolean bigIntegerLess(BigInteger toMatch, BigInteger value) {
    return toMatch.compareTo(value) <= (inclusive ? 0 : -1);
  }

  @Specialization
  @SuppressWarnings("unused")
  protected boolean objectLess(Object toMatch, Object value) {
    throw new TypeError("Cannot order " + value.getClass());
  }

  public static LessThanMatcherNode create(boolean inclusive, ValueNode toMatchNode, ValueNode valueNode) {
    return LessThanMatcherNodeGen.create(inclusive, toMatchNode, valueNode);
  }
}
