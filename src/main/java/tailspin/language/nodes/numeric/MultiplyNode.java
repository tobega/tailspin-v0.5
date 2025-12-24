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

public abstract class MultiplyNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode leftNode;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode rightNode;

  protected final boolean isUntypedRegion;

  MultiplyNode(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion,
      SourceSection sourceSection) {
    super(sourceSection);
    this.leftNode = leftNode;
    this.rightNode = rightNode;
    this.isUntypedRegion = isUntypedRegion;
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
    @TruffleBoundary
    protected SmallSciNum doSmallSciNum(SmallSciNum left, SmallSciNum right) {
      return left.multiply(right);
    }

    protected boolean isSmallEnough(long value) {
      return Math.abs(value) <= SmallSciNum.MAX_MANTISSA;
    }

    @Specialization(guards = "isSmallEnough(right)")
    @TruffleBoundary
    protected SmallSciNum doSmallSciNumLong(SmallSciNum left, Long right) {
      return left.multiply(SmallSciNum.fromLong(right));
    }

    @Specialization(guards = "isSmallEnough(left)")
    @TruffleBoundary
    protected SmallSciNum doLongSmallSciNum(Long left, SmallSciNum right) {
      return SmallSciNum.fromLong(left).multiply(right);
    }

    @Specialization
    @TruffleBoundary
    protected SciNum doSciNum(SciNum left, SciNum right) {
      return left.multiply(right);
    }

    @Specialization
    @TruffleBoundary
    protected Object doRationalSciNum(Rational left, SciNum right) {
      return SciNum.fromBigInteger(left.numerator()).multiply(right).divide(SciNum.fromBigInteger(left.denominator()));
    }

    @Specialization
    @TruffleBoundary
    protected Object doSciNumRational(SciNum left, Rational right) {
      return left.multiply(SciNum.fromBigInteger(right.numerator())).divide(SciNum.fromBigInteger(right.denominator()));
    }

    @Specialization
    protected Object typeError(Object left, Object right) {
      throw new TypeError("Cannot multiply " + left + " and " + right, this);
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

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasures(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoMultiplyNode doMultiplyNode) {
    return doMultiplyNode.executeMultiply(frame, this, left.value(), right.value());
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasureRight(VirtualFrame frame, Object left, Measure right,
      @Cached(inline = true) @Shared DoMultiplyNode doMultiplyNode) {
    return doMultiplyNode.executeMultiply(frame, this, left, right.value());
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasureLeft(VirtualFrame frame, Measure left, Object right,
      @Cached(inline = true) @Shared DoMultiplyNode doMultiplyNode) {
    return doMultiplyNode.executeMultiply(frame, this, left.value(), right);
  }

  @Specialization
  protected Object doOther(VirtualFrame frame, Object left, Object right,
      @Cached(inline = true) @Shared DoMultiplyNode doMultiplyNode) {
    return doMultiplyNode.executeMultiply(frame, this, left, right);
  }

  public static MultiplyNode create(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion,
      SourceSection section) {
    return MultiplyNodeGen.create(leftNode, rightNode, isUntypedRegion, section);
  }
}
