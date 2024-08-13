package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;

@NodeChild(value = "dummy", type = ValueNode.class)
@NodeChild(value = "valueNode", type = ValueNode.class)
public abstract class EqualityMatcherNode extends MatcherNode {
  @Specialization
  protected boolean longEquals(long toMatch, long value) {
    return toMatch == value;
  }

  @Specialization(replaces = "longEquals")
  @TruffleBoundary
  protected boolean bigNumberEquals(BigNumber toMatch, BigNumber value) {
    return toMatch.equals(value);
  }

  @Specialization(guards = {"toMatch.unit() == value.unit()", "toMatch.isLong()", "value.isLong()"})
  protected boolean doMeasureLong(Measure toMatch, Measure value) {
    return longEquals((long) toMatch.value(), (long) value.value());
  }

  @Specialization(guards = "toMatch.unit() == value.unit()")
  protected boolean doMeasureBigNumber(Measure toMatch, Measure value) {
    return bigNumberEquals(toMatch.bigNumber(), value.bigNumber());
  }

  @Specialization
  protected boolean objectEquals(Object toMatch, Object value) {
    return toMatch.equals(value);
  }

  public static EqualityMatcherNode create(ValueNode valueNode) {
    return EqualityMatcherNodeGen.create(null, valueNode);
  }
}
