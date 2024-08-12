package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;

@NodeChild("leftNode") @NodeChild("rightNode")
public abstract class SubtractNode extends ValueNode {
  public abstract Object executeSubtract(Object left, Object right);

  @Specialization(rewriteOn = ArithmeticException.class)
  protected long doLong(long left, long right) {
    return Math.subtractExact(left, right);
  }

  @Specialization
  @TruffleBoundary
  protected BigNumber doBigNumber(BigNumber left, BigNumber right) {
    return left.subtract(right);
  }

  @Specialization(guards = "left.unit().equals(right.unit())")
  protected Measure doMeasure(Measure left, Measure right) {
    return new Measure(executeSubtract(left.value(), right.value()), left.unit());
  }

  @Fallback
  protected Object typeError(Object left, Object right) {
    throw TypeError.at(this, left, right);
  }

  public static SubtractNode create(ValueNode leftNode, ValueNode rightNode) {
    return SubtractNodeGen.create(leftNode, rightNode);
  }
}
