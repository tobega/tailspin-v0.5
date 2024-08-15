package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.SciNum;

@NodeChild("leftNode") @NodeChild("rightNode")
public abstract class SubtractNode extends ValueNode {

  @Specialization(rewriteOn = ArithmeticException.class)
  protected long doLong(long left, long right) {
    return Math.subtractExact(left, right);
  }

  @Specialization
  @TruffleBoundary
  protected BigNumber doBigNumber(BigNumber left, BigNumber right) {
    return left.subtract(right);
  }

  @Specialization
  @TruffleBoundary
  protected SciNum doSciNum(SciNum left, SciNum right) {
    return left.subtract(right);
  }

  @Specialization(guards = {"left.unit() == right.unit()", "left.isLong()", "right.isLong()"})
  protected Measure doMeasureLong(Measure left, Measure right) {
    return new Measure(doLong((long) left.value(), (long) right.value()), left.unit());
  }

  @Specialization(guards = "left.unit() == right.unit()")
  protected Measure doMeasureBigNumber(Measure left, Measure right) {
    return new Measure(doBigNumber(left.bigNumber(), right.bigNumber()), left.unit());
  }

  @Fallback
  protected Object typeError(Object left, Object right) {
    throw TypeError.at(this, left, right);
  }

  public static SubtractNode create(ValueNode leftNode, ValueNode rightNode) {
    return SubtractNodeGen.create(leftNode, rightNode);
  }
}
