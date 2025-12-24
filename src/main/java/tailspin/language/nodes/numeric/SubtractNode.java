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
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.TypeError;
import tailspin.language.nodes.TailspinTypes;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.SmallSciNum;

public abstract class SubtractNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode leftNode;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode rightNode;

  protected final boolean isUntypedRegion;

  SubtractNode(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion,
      SourceSection sourceSection) {
    super(sourceSection);
    this.leftNode = leftNode;
    this.rightNode = rightNode;
    this.isUntypedRegion = isUntypedRegion;
  }

  @GenerateInline
  @TypeSystemReference(TailspinTypes.class)
  public static abstract class DoSubtractNode extends Node {
    public abstract Object executeSubtract(VirtualFrame frame, Node node, Object left, Object right);

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
    protected Object doRational(Rational left, Rational right) {
      return left.subtract(right).simplestForm();
    }

    @Specialization
    @TruffleBoundary
    protected SmallSciNum doSmallSciNum(SmallSciNum left, SmallSciNum right) {
      return left.subtract(right);
    }

    protected boolean isSmallEnough(long value) {
      return Math.abs(value) <= SmallSciNum.MAX_MANTISSA;
    }

    @Specialization(guards = "isSmallEnough(right)")
    @TruffleBoundary
    protected SmallSciNum doSmallSciNumLong(SmallSciNum left, Long right) {
      return left.subtract(SmallSciNum.fromLong(right));
    }

    @Specialization(guards = "isSmallEnough(left)")
    @TruffleBoundary
    protected SmallSciNum doLongSmallSciNum(Long left, SmallSciNum right) {
      return SmallSciNum.fromLong(left).subtract(right);
    }

    @Specialization
    @TruffleBoundary
    protected SciNum doSciNum(SciNum left, SciNum right) {
      return left.subtract(right);
    }

    @Specialization
    @TruffleBoundary
    protected Object doRationalSciNum(Rational left, SciNum right) {
      return SciNum.fromBigInteger(left.numerator()).subtract(SciNum.fromBigInteger(left.denominator()).multiply(right)).divide(SciNum.fromBigInteger(left.denominator()));
    }

    @Specialization
    @TruffleBoundary
    protected Object doSciNumRational(SciNum left, Rational right) {
      return left.multiply(SciNum.fromBigInteger(right.denominator())).subtract(SciNum.fromBigInteger(right.numerator())).divide(SciNum.fromBigInteger(right.denominator()));
    }

    @Specialization
    protected Object typeError(Object left, Object right) {
      throw new TypeError("Cannot subtract " + left + " and " + right, this);
    }
  }

  @Specialization(guards = "left.unit() == right.unit()")
  protected Measure doMeasure(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoSubtractNode doSubtractNode) {
    return new Measure(doSubtractNode.executeSubtract(frame, this, left.value(), right.value()), left.unit());
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasures(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoSubtractNode doSubtractNode) {
    return doSubtractNode.executeSubtract(frame, this, left.value(), right.value());
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasureRight(VirtualFrame frame, Object left, Measure right,
      @Cached(inline = true) @Shared DoSubtractNode doSubtractNode) {
    return doSubtractNode.executeSubtract(frame, this, left, right.value());
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasureLeft(VirtualFrame frame, Measure left, Object right,
      @Cached(inline = true) @Shared DoSubtractNode doSubtractNode) {
    return doSubtractNode.executeSubtract(frame, this, left.value(), right);
  }

  @Specialization
  protected Object doOther(VirtualFrame frame, Object left, Object right,
      @Cached(inline = true) @Shared DoSubtractNode doSubtractNode) {
    return doSubtractNode.executeSubtract(frame, this, left, right);
  }

  public static SubtractNode create(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion,
      SourceSection section) {
    return SubtractNodeGen.create(leftNode, rightNode, isUntypedRegion, section);
  }
}
