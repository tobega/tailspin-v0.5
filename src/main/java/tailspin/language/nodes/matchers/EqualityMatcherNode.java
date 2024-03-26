package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import java.math.BigInteger;
import tailspin.language.nodes.ExpressionNode;
import tailspin.language.nodes.MatcherNode;

@NodeChild(value = "toMatchNode", type = ExpressionNode.class)
@NodeChild(value = "valueNode", type = ExpressionNode.class)
public abstract class EqualityMatcherNode extends MatcherNode {
  @Specialization
  protected boolean longEquals(long toMatch, long value) {
    return toMatch == value;
  }

  @Specialization(replaces = "longEquals")
  @TruffleBoundary
  protected boolean bigIntegerEquals(BigInteger toMatch, BigInteger value) {
    return toMatch.equals(value);
  }

  @Fallback
  protected boolean objectEquals(Object toMatch, Object value) {
    return toMatch.equals(value);
  }
}
