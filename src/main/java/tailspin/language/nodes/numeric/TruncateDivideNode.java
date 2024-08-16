package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.TypeError;
import tailspin.language.nodes.TailspinTypes;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;

@NodeChild("leftNode") @NodeChild("rightNode")
public abstract class TruncateDivideNode extends ValueNode {

  @GenerateInline
  @TypeSystemReference(TailspinTypes.class)
  public static abstract class DoTruncateDivideNode extends Node {
    public abstract Object executeTruncateDivide(VirtualFrame frame, Node node, Object left, Object right);

    @Specialization(rewriteOn = ArithmeticException.class)
    protected long doLong(long left, long right) {
      return Math.divideExact(left, right);
    }

    @Specialization
    @TruffleBoundary
    protected BigNumber doBigNumber(BigNumber left, BigNumber right) {
      return left.divide(right);
    }

    @Specialization
    protected Object typeError(Object left, Object right) {
      throw new TypeError("Cannot divide " + left + " and " + right);
    }
  }

  @Specialization(guards = "canMultiplyUnits(left.unit(), right.unit())")
  protected Measure doMeasure(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoTruncateDivideNode doTruncateDivideNode) {
    return new Measure(doTruncateDivideNode.executeTruncateDivide(frame, this, left.value(), right.value()), left.unit());
  }

  @Specialization
  protected Object doUntyped(VirtualFrame frame, Object left, Object right,
      @Cached(inline = true) @Shared DoTruncateDivideNode doTruncateDivideNode) {
    return doTruncateDivideNode.executeTruncateDivide(frame, this, left, right);
  }

  boolean canMultiplyUnits(Object leftUnit, Object rightUnit) {
    return leftUnit == Measure.SCALAR || rightUnit == Measure.SCALAR;
  }
  public static TruncateDivideNode create(ValueNode leftNode, ValueNode rightNode) {
    return TruncateDivideNodeGen.create(leftNode, rightNode);
  }
}
