package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;

@NodeChild("leftNode") @NodeChild("rightNode")
public abstract class MathModNode extends ValueNode {
  @Specialization(rewriteOn = ArithmeticException.class)
  protected long doLong(long left, long right) {
    return Math.floorMod(left, Math.absExact(right));
  }

  @Specialization
  @TruffleBoundary
  protected BigNumber doBigNumber(BigNumber left, BigNumber right) {
    return left.mod(right);
  }

  @Specialization(guards = {"left.unit() == right.unit()", "left.isLong()", "right.isLong()"})
  protected Measure doMeasureLong(Measure left, Measure right) {
    return new Measure(doLong((long) left.value(), (long) right.value()), left.unit());
  }

  @Specialization(guards = "left.unit() == right.unit()")
  protected Measure doMeasureBigNumber(Measure left, Measure right) {
    return new Measure(doBigNumber(left.bigNumber(), right.bigNumber()), left.unit());
  }

  @Specialization
  protected Object typeError(Object left, Object right) {
    throw TypeError.at(this, left, right);
  }

  public static MathModNode create(ValueNode leftNode, ValueNode rightNode) {
    return MathModNodeGen.create(leftNode, rightNode);
  }
}
