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

public abstract class MathModNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode leftNode;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode rightNode;

  protected final boolean isUntypedRegion;

  MathModNode(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion) {
    this.leftNode = leftNode;
    this.rightNode = rightNode;
    this.isUntypedRegion = isUntypedRegion;
  }

  @GenerateInline
  @TypeSystemReference(TailspinTypes.class)
  public static abstract class DoMathModNode extends Node {
    public abstract Object executeMathMod(VirtualFrame frame, Node node, Object left, Object right);

    @Specialization(rewriteOn = ArithmeticException.class)
    protected long doLong(long left, long right) {
      return Math.floorMod(left, Math.absExact(right));
    }

    @Specialization
    @TruffleBoundary
    protected BigNumber doBigNumber(BigNumber left, BigNumber right) {
      return left.mod(right);
    }

    @Specialization
    @TruffleBoundary
    protected Object doRational(Rational left, Rational right) {
      return left.mod(right).simplestForm();
    }

    @Specialization
    @TruffleBoundary
    protected SciNum doSciNum(SciNum left, SciNum right) {
      return left.mod(right);
    }

    @Specialization
    @TruffleBoundary
    protected Object doRationalSciNum(Rational left, SciNum right) {
      return SciNum.fromBigInteger(left.numerator()).mod(right.multiply(SciNum.fromBigInteger(left.denominator()))).divide(SciNum.fromBigInteger(left.denominator()));
    }

    @Specialization
    @TruffleBoundary
    protected Object doSciNumRational(SciNum left, Rational right) {
      return left.multiply(SciNum.fromBigInteger(right.denominator())).mod(SciNum.fromBigInteger(right.numerator())).divide(SciNum.fromBigInteger(right.denominator()));
    }

    @Specialization
    protected Object typeError(Object left, Object right) {
      throw new TypeError("Cannot math mod " + left + " and " + right);
    }
  }

  @Specialization(guards = "left.unit() == right.unit()")
  protected Measure doMeasure(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoMathModNode doMathModNode) {
    return new Measure(doMathModNode.executeMathMod(frame, this, left.value(), right.value()), left.unit());
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasures(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoMathModNode doMathModNode) {
    return doMathModNode.executeMathMod(frame, this, left.value(), right.value());
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasureRight(VirtualFrame frame, Object left, Measure right,
      @Cached(inline = true) @Shared DoMathModNode doMathModNode) {
    return doMathModNode.executeMathMod(frame, this, left, right.value());
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasureLeft(VirtualFrame frame, Measure left, Object right,
      @Cached(inline = true) @Shared DoMathModNode doMathModNode) {
    return doMathModNode.executeMathMod(frame, this, left.value(), right);
  }

  @Specialization
  protected Object doOther(VirtualFrame frame, Object left, Object right,
      @Cached(inline = true) @Shared DoMathModNode doMathModNode) {
    return doMathModNode.executeMathMod(frame, this, left, right);
  }

  public static MathModNode create(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion) {
    return MathModNodeGen.create(leftNode, rightNode, isUntypedRegion);
  }

  @Override
  public MatcherNode getTypeMatcher() {
    MatcherNode typeMatcher = leftNode.getTypeMatcher();
    if (typeMatcher == null) typeMatcher = rightNode.getTypeMatcher();
    return typeMatcher;
  }
}
