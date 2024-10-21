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
import java.math.BigInteger;
import tailspin.language.TypeError;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.TailspinTypes;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;

public abstract class DivideNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode leftNode;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode rightNode;

  protected final boolean isUntypedRegion;

  DivideNode(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion) {
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
    protected SciNum doSciNum(SciNum left, SciNum right) {
      return left.divide(right);
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
      throw new TypeError("Cannot divide " + left + " and " + right);
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

  public static DivideNode create(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion) {
    return DivideNodeGen.create(leftNode, rightNode, isUntypedRegion);
  }

  @Override
  public MatcherNode getTypeMatcher() {
    MatcherNode typeMatcher = leftNode.getTypeMatcher();
    if (typeMatcher == null) typeMatcher = rightNode.getTypeMatcher();
    return typeMatcher;
  }
}
