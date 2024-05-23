package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import java.math.BigInteger;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;

@NodeChild(value = "toMatchNode", type = ValueNode.class)
@NodeChild(value = "valueNode", type = ValueNode.class)
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

  @Specialization
  protected boolean objectEquals(Object toMatch, Object value) {
    return toMatch.equals(value);
  }
}
