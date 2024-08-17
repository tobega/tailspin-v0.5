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
public abstract class GreaterThanMatcherNode extends MatcherNode {

  private final boolean inclusive;

  protected GreaterThanMatcherNode(boolean inclusive) {
    super(null);
    this.inclusive = inclusive;
  }

  @Specialization
  protected boolean longMore(long toMatch, long value) {
    return toMatch > value || inclusive && toMatch == value;
  }

  @Specialization(replaces = "longMore")
  @TruffleBoundary
  protected boolean bigNumberMore(BigNumber toMatch, BigNumber value) {
    return toMatch.compareTo(value) >= (inclusive ? 0 : 1);
  }

  @Specialization
  @SuppressWarnings("unused")
  protected boolean objectMore(Object toMatch, Object value) {
    throw new TypeError("Cannot order " + value.getClass());
  }

  public static GreaterThanMatcherNode create(boolean inclusive, ValueNode valueNode) {
    return GreaterThanMatcherNodeGen.create(inclusive, null, valueNode);
  }
}
