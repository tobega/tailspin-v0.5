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
import java.math.BigInteger;
import tailspin.language.TypeError;
import tailspin.language.nodes.TailspinTypes;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.SmallRational;
import tailspin.language.runtime.SmallSciNum;

public abstract class MathModNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode leftNode;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode rightNode;

  protected final boolean isUntypedRegion;

  MathModNode(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion,
      SourceSection sourceSection) {
    super(sourceSection);
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

    @Specialization(replaces = "doLong")
    @TruffleBoundary
    protected BigNumber doBigNumber(BigNumber left, BigNumber right) {
      return left.mod(right);
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallRational doSmallRational(SmallRational left, SmallRational right) {
      return left.mod(right);
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallRational smallRationalLong(SmallRational left, long value) {
      return left.mod(SmallRational.of(value, 1L));
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallRational longSmallRational(long left, SmallRational value) {
      return SmallRational.of(left, 1L).mod(value);
    }

    @Specialization(replaces = "doSmallRational")
    @TruffleBoundary
    protected Rational doRational(Rational left, Rational right) {
      return left.mod(right);
    }

    @Specialization(replaces = "smallRationalLong")
    @TruffleBoundary
    protected Rational rationalBigNumber(Rational left, BigNumber value) {
      return left.mod(new Rational(value.asBigInteger(), BigInteger.ONE));
    }

    @Specialization(replaces = "longSmallRational")
    @TruffleBoundary
    protected Rational bigNumberRational(BigNumber left, Rational value) {
      return new Rational(left.asBigInteger(), BigInteger.ONE).mod(value);
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallSciNum doSmallSciNum(SmallSciNum left, SmallSciNum right) {
      return left.mod(right);
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallSciNum doSmallSciNumLong(SmallSciNum left, Long right) {
      return left.mod(SmallSciNum.fromLong(right));
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallSciNum doLongSmallSciNum(Long left, SmallSciNum right) {
      return SmallSciNum.fromLong(left).mod(right);
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallSciNum doSmallRationalSmallSciNum(SmallRational left, SmallSciNum right) {
      return SmallSciNum.fromLong(left.numerator()).mod(right.multiply(SmallSciNum.fromLong(left.denominator()))).divide(SmallSciNum.fromLong(left.denominator()));
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallSciNum doSmallSciNumSmallRational(SmallSciNum left, SmallRational right) {
      return left.multiply(SmallSciNum.fromLong(right.denominator())).mod(SmallSciNum.fromLong(right.numerator())).divide(SmallSciNum.fromLong(right.denominator()));
    }

    @Specialization(replaces = "doSmallSciNum")
    @TruffleBoundary
    protected SciNum doSciNum(SciNum left, SciNum right) {
      return left.mod(right);
    }

    @Specialization(replaces = "doSmallSciNumLong")
    @TruffleBoundary
    protected SciNum doSciNumBigNum(SciNum left, BigNumber right) {
      return left.mod(SciNum.fromBigInteger(right.asBigInteger()));
    }

    @Specialization(replaces = "doLongSmallSciNum")
    @TruffleBoundary
    protected SciNum doBigNumSciNum(BigNumber left, SciNum right) {
      return SciNum.fromBigInteger(left.asBigInteger()).mod(right);
    }

    @Specialization(replaces = "doSmallRationalSmallSciNum")
    @TruffleBoundary
    protected SciNum doRationalSciNum(Rational left, SciNum right) {
      return SciNum.fromBigInteger(left.numerator()).mod(right.multiply(SciNum.fromBigInteger(left.denominator()))).divide(SciNum.fromBigInteger(left.denominator()));
    }

    @Specialization(replaces = "doSmallSciNumSmallRational")
    @TruffleBoundary
    protected SciNum doSciNumRational(SciNum left, Rational right) {
      return left.multiply(SciNum.fromBigInteger(right.denominator())).mod(SciNum.fromBigInteger(right.numerator())).divide(SciNum.fromBigInteger(right.denominator()));
    }

    @Specialization
    protected Object typeError(Object left, Object right) {
      throw new TypeError("Cannot math mod " + left + " and " + right, this);
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

  public static MathModNode create(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion,
      SourceSection section) {
    return MathModNodeGen.create(leftNode, rightNode, isUntypedRegion, section);
  }
}
