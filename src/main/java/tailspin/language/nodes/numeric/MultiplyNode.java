package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;

@NodeChild("leftNode") @NodeChild("rightNode")
public abstract class MultiplyNode extends ValueNode {
  @Specialization(rewriteOn = ArithmeticException.class)
  protected long doLong(long left, long right) {
    return Math.multiplyExact(left, right);
  }

  @Specialization
  @TruffleBoundary
  protected BigNumber doBigNumber(BigNumber left, BigNumber right) {
    return left.multiply(right);
  }

  @Specialization
  protected Object typeError(Object left, Object right) {
    throw TypeError.at(this, left, right);
  }

  public static MultiplyNode create(ValueNode leftNode, ValueNode rightNode) {
    return MultiplyNodeGen.create(leftNode, rightNode);
  }
}
