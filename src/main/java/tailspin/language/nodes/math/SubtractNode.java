package tailspin.language.nodes.math;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import java.math.BigInteger;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;

@NodeChild("leftNode") @NodeChild("rightNode")
public abstract class SubtractNode extends ValueNode {
  @Specialization(rewriteOn = ArithmeticException.class)
  protected long doLong(long left, long right) {
    return Math.subtractExact(left, right);
  }

  @Specialization
  @TruffleBoundary
  protected BigInteger doBigInteger(BigInteger left, BigInteger right) {
    return left.subtract(right);
  }

  @Fallback
  protected Object typeError(Object left, Object right) {
    throw TypeError.at(this, left, right);
  }

  public static SubtractNode create(ValueNode leftNode, ValueNode rightNode) {
    return SubtractNodeGen.create(leftNode, rightNode);
  }
}
