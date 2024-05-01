package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import java.math.BigInteger;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;

@NodeChild("leftNode") @NodeChild("rightNode")
public abstract class AddNode extends ValueNode {
  @Specialization(rewriteOn = ArithmeticException.class)
  protected long doLong(long left, long right) {
    return Math.addExact(left, right);
  }

  @Specialization
  @TruffleBoundary
  protected BigInteger doBigInteger(BigInteger left, BigInteger right) {
    return left.add(right);
  }

  @Fallback
  protected Object typeError(Object left, Object right) {
    throw TypeError.at(this, left, right);
  }
}
