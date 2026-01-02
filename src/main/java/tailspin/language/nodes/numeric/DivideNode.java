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

public abstract class DivideNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode leftNode;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode rightNode;

  protected final boolean isUntypedRegion;

  DivideNode(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion,
      SourceSection sourceSection) {
    super(sourceSection);
    this.leftNode = leftNode;
    this.rightNode = rightNode;
    this.isUntypedRegion = isUntypedRegion;
  }

  @GenerateInline
  @TypeSystemReference(TailspinTypes.class)
  public static abstract class DoDivideNode extends Node {
    public abstract Object executeDivide(VirtualFrame frame, Node node, Object left, Object right);

    @Specialization
    protected SmallRational doCreateRational(long numerator, long denominator) {
      return SmallRational.of(numerator, denominator);
    }

    @Specialization
    protected Rational doCreateBigRational(BigNumber numerator, BigNumber denominator) {
      return new Rational(numerator.asBigInteger(), denominator.asBigInteger());
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallRational doSmallRational(SmallRational left, SmallRational right) {
      return left.divide(right);
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallRational smallRationalLong(SmallRational left, long value) {
      return left.divide(SmallRational.of(value, 1L));
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallRational longSmallRational(long left, SmallRational value) {
      return SmallRational.of(left, 1L).divide(value);
    }

    @Specialization(replaces = "doSmallRational")
    @TruffleBoundary
    protected Rational doRational(Rational left, Rational right) {
      return left.divide(right);
    }

    @Specialization(replaces = "smallRationalLong")
    @TruffleBoundary
    protected Rational rationalBigNumber(Rational left, BigNumber value) {
      return left.divide(new Rational(value.asBigInteger(), BigInteger.ONE));
    }

    @Specialization(replaces = "longSmallRational")
    @TruffleBoundary
    protected Rational bigNumberRational(BigNumber left, Rational value) {
      return new Rational(left.asBigInteger(), BigInteger.ONE).divide(value);
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    protected SmallSciNum doSmallSciNum(SmallSciNum left, SmallSciNum right) {
      return left.divide(right);
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    protected SmallSciNum doSmallSciNumLong(SmallSciNum left, Long right) {
      return left.divide(SmallSciNum.fromLong(right));
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    protected SmallSciNum doLongSmallSciNum(Long left, SmallSciNum right) {
      return SmallSciNum.fromLong(left).divide(right);
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallSciNum doSmallRationalSmallSciNum(SmallRational left, SmallSciNum right) {
      return SmallSciNum.fromLong(left.numerator()).divide(SmallSciNum.fromLong(left.denominator()).multiply(right));
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallSciNum doSmallSciNumSmallRational(SmallSciNum left, SmallRational right) {
      return left.multiply(SmallSciNum.fromLong(right.denominator())).divide(SmallSciNum.fromLong(right.numerator()));
    }

    @Specialization(replaces = "doSmallSciNum")
    @TruffleBoundary
    protected SciNum doSciNum(SciNum left, SciNum right) {
      return left.divide(right);
    }

    @Specialization(replaces = "doLongSmallSciNum")
    @TruffleBoundary
    protected SciNum doBigNumSciNum(BigNumber left, SciNum right) {
      return SciNum.fromBigInteger(left.asBigInteger()).divide(right);
    }

    @Specialization(replaces = "doSmallSciNumLong")
    @TruffleBoundary
    protected SciNum doSciNumBigNum(SciNum left, BigNumber right) {
      return left.multiply(SciNum.fromBigInteger(right.asBigInteger()));
    }

    @Specialization(replaces = "doSmallRationalSmallSciNum")
    @TruffleBoundary
    protected SciNum doRationalSciNum(Rational left, SciNum right) {
      return SciNum.fromBigInteger(left.numerator()).divide(SciNum.fromBigInteger(left.denominator()).multiply(right));
    }

    @Specialization(replaces = "doSmallSciNumSmallRational")
    @TruffleBoundary
    protected SciNum doSciNumRational(SciNum left, Rational right) {
      return left.multiply(SciNum.fromBigInteger(right.denominator())).divide(SciNum.fromBigInteger(right.numerator()));
    }

    @Specialization
    protected Object typeError(Object left, Object right) {
      throw new TypeError("Cannot divide " + left + " and " + right, this);
    }
  }

  @Specialization(guards = "isScalar(right.unit())")
  protected Measure doMeasure(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoDivideNode doDivideNode) {
    return new Measure(doDivideNode.executeDivide(frame, this, left.value(), right.value()), left.unit());
  }

  boolean isScalar(Object unit) {
    return unit == Measure.SCALAR;
  }

  @Specialization(guards = "left.unit() == right.unit()")
  protected Measure doSameMeasure(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoDivideNode doDivideNode) {
    return new Measure(doDivideNode.executeDivide(frame, this, left.value(), right.value()), Measure.SCALAR);
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasures(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoDivideNode doDivideNode) {
    return doDivideNode.executeDivide(frame, this, left.value(), right.value());
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasureRight(VirtualFrame frame, Object left, Measure right,
      @Cached(inline = true) @Shared DoDivideNode doDivideNode) {
    return doDivideNode.executeDivide(frame, this, left, right.value());
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasureLeft(VirtualFrame frame, Measure left, Object right,
      @Cached(inline = true) @Shared DoDivideNode doDivideNode) {
    return doDivideNode.executeDivide(frame, this, left.value(), right);
  }

  @Specialization
  protected Object doOther(VirtualFrame frame, Object left, Object right,
      @Cached(inline = true) @Shared DoDivideNode doDivideNode) {
    return doDivideNode.executeDivide(frame, this, left, right);
  }

  public static DivideNode create(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion,
      SourceSection section) {
    return DivideNodeGen.create(leftNode, rightNode, isUntypedRegion, section);
  }
}
