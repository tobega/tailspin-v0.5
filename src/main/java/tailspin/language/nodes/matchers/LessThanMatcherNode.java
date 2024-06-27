package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.TypeError;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;

@NodeChild(value = "dummy", type = ValueNode.class)
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
  protected boolean bigNumberLess(BigNumber toMatch, BigNumber value) {
    return toMatch.compareTo(value) <= (inclusive ? 0 : -1);
  }

  @Specialization
  @SuppressWarnings("unused")
  protected boolean objectLess(Object toMatch, Object value) {
    throw new TypeError("Cannot order " + value.getClass());
  }

  public static LessThanMatcherNode create(boolean inclusive, ValueNode valueNode) {
    return LessThanMatcherNodeGen.create(inclusive, null, valueNode);
  }
}
