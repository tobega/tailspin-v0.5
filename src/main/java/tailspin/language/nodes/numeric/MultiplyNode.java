package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.TypeError;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.TailspinTypes;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;

public abstract class MultiplyNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode leftNode;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode rightNode;

  MultiplyNode(ValueNode leftNode, ValueNode rightNode) {
    this.leftNode = leftNode;
    this.rightNode = rightNode;
  }

  @GenerateInline
  @TypeSystemReference(TailspinTypes.class)
  public static abstract class DoMultiplyNode extends Node {
    public abstract Object executeMultiply(VirtualFrame frame, Node node, Object left, Object right);

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
    @TruffleBoundary
    protected Object doRational(Rational left, Rational right) {
      return left.multiply(right).simplestForm();
    }

    @Specialization
    protected Object doRationalSciNum(Rational left, SciNum right) {
      return SciNum.fromBigInteger(left.numerator()).multiply(right).divide(SciNum.fromBigInteger(left.denominator()));
    }

    @Specialization
    protected Object doSciNumRational(SciNum left, Rational right) {
      return left.multiply(SciNum.fromBigInteger(right.numerator())).divide(SciNum.fromBigInteger(right.denominator()));
    }

    @Specialization
    @TruffleBoundary
    protected SciNum doSciNum(SciNum left, SciNum right) {
      return left.multiply(right);
    }

    @Specialization
    protected Object typeError(Object left, Object right) {
      throw new TypeError("Cannot multiply " + left + " and " + right);
    }
  }

  @Specialization(guards = "isScalar(right.unit())")
  protected Measure doMeasureLeft(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoMultiplyNode doMultiplyNode) {
    return new Measure(doMultiplyNode.executeMultiply(frame, this, left.value(), right.value()), left.unit());
  }

  boolean isScalar(Object unit) {
    return unit == Measure.SCALAR;
  }

  @Specialization(guards = "isScalar(left.unit())")
  protected Measure doMeasureRight(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoMultiplyNode doMultiplyNode) {
    return new Measure(doMultiplyNode.executeMultiply(frame, this, left.value(), right.value()), right.unit());
  }

  @Specialization
  protected Object doUntyped(VirtualFrame frame, Object left, Object right,
      @Cached(inline = true) @Shared DoMultiplyNode doMultiplyNode) {
    return doMultiplyNode.executeMultiply(frame, this, left, right);
  }

  public static MultiplyNode create(ValueNode leftNode, ValueNode rightNode) {
    return MultiplyNodeGen.create(leftNode, rightNode);
  }

  @Override
  public MatcherNode getTypeMatcher() {
    MatcherNode typeMatcher = leftNode.getTypeMatcher();
    if (typeMatcher == null) typeMatcher = rightNode.getTypeMatcher();
    return typeMatcher;
  }
}
