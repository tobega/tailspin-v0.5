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
    protected Object doCreateRational(long numerator, long denominator) {
      return new Rational(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator)).simplestForm();
    }

    @Specialization
    protected Object doCreateBigRational(BigNumber numerator, BigNumber denominator) {
      return new Rational(numerator.asBigInteger(), denominator.asBigInteger()).simplestForm();
    }

    @Specialization
    @TruffleBoundary
    protected Object doRational(Rational left, Rational right) {
      return left.divide(right).simplestForm();
    }

    @Specialization
    @TruffleBoundary
    protected Object rationalBigNumber(Rational left, BigNumber value) {
      return left.divide(new Rational(value.asBigInteger(), BigInteger.ONE));
    }

    @Specialization
    @TruffleBoundary
    protected Object bigNumberRational(BigNumber left, Rational value) {
      return new Rational(left.asBigInteger(), BigInteger.ONE).divide(value);
    }

    @Specialization
    @TruffleBoundary
    protected Object doSmallSciNum(SmallSciNum left, SmallSciNum right) {
      return left.divide(right);
    }

    @Specialization
    @TruffleBoundary
    protected Object doSmallSciNumLong(SmallSciNum left, Long right) {
      return left.divide(SmallSciNum.fromLong(right));
    }

    @Specialization
    @TruffleBoundary
    protected Object doLongSmallSciNum(Long left, SmallSciNum right) {
      return SmallSciNum.fromLong(left).divide(right);
    }

    @Specialization
    @TruffleBoundary
    protected SciNum doSciNum(SciNum left, SciNum right) {
      return left.divide(right);
    }

    @Specialization
    @TruffleBoundary
    protected Object doBigNumSciNum(BigNumber left, SciNum right) {
      return SciNum.fromBigInteger(left.asBigInteger()).divide(right);
    }

    @Specialization
    @TruffleBoundary
    protected Object doSciNumBigNum(SciNum left, BigNumber right) {
      return left.multiply(SciNum.fromBigInteger(right.asBigInteger()));
    }

    @Specialization
    @TruffleBoundary
    protected Object doRationalSciNum(Rational left, SciNum right) {
      return SciNum.fromBigInteger(left.numerator()).divide(SciNum.fromBigInteger(left.denominator()).multiply(right));
    }

    @Specialization
    @TruffleBoundary
    protected Object doSciNumRational(SciNum left, Rational right) {
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
