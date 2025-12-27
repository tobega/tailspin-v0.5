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

public abstract class AddNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode leftNode;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode rightNode;

  protected final boolean isUntypedRegion;

  AddNode(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion,
      SourceSection sourceSection) {
    super(sourceSection);
    this.leftNode = leftNode;
    this.rightNode = rightNode;
    this.isUntypedRegion = isUntypedRegion;
  }

  public abstract Object executeAdd(VirtualFrame frame, Object left, Object right);

  @GenerateInline
  @TypeSystemReference(TailspinTypes.class)
  public static abstract class DoAddNode extends Node {
    public abstract Object executeAdd(VirtualFrame frame, Node node, Object left, Object right);

    @Specialization(rewriteOn = ArithmeticException.class)
    protected long doLong(long left, long right) {
      return Math.addExact(left, right);
    }

    @Specialization
    @TruffleBoundary
    protected BigNumber doBigNumber(BigNumber left, BigNumber right) {
      return left.add(right);
    }

    @Specialization
    @TruffleBoundary
    protected Object doRational(Rational left, Rational right) {
      return left.add(right).simplestForm();
    }

    @Specialization
    @TruffleBoundary
    protected Object rationalBigNumber(Rational left, BigNumber value) {
      return left.add(new Rational(value.asBigInteger(), BigInteger.ONE));
    }

    @Specialization
    @TruffleBoundary
    protected Object bigNumberRational(BigNumber left, Rational value) {
      return new Rational(left.asBigInteger(), BigInteger.ONE).add(value);
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallSciNum doSmallSciNum(SmallSciNum left, SmallSciNum right) {
      return left.add(right);
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallSciNum doSmallSciNumLong(SmallSciNum left, Long right) {
      return left.add(SmallSciNum.fromLong(right));
    }

    @Specialization(rewriteOn = ArithmeticException.class)
    @TruffleBoundary
    protected SmallSciNum doLongSmallSciNum(Long left, SmallSciNum right) {
      return SmallSciNum.fromLong(left).add(right);
    }

    @Specialization
    @TruffleBoundary
    protected SciNum doSciNum(SciNum left, SciNum right) {
      return left.add(right);
    }

    @Specialization
    @TruffleBoundary
    protected Object doBigNumSciNum(BigNumber left, SciNum right) {
      return SciNum.fromBigInteger(left.asBigInteger()).add(right);
    }

    @Specialization
    @TruffleBoundary
    protected Object doSciNumBigNum(SciNum left, BigNumber right) {
      return left.add(SciNum.fromBigInteger(right.asBigInteger()));
    }

    @Specialization
    @TruffleBoundary
    protected Object doRationalSciNum(Rational left, SciNum right) {
      return SciNum.fromBigInteger(left.numerator()).add(SciNum.fromBigInteger(left.denominator()).multiply(right)).divide(SciNum.fromBigInteger(left.denominator()));
    }

    @Specialization
    @TruffleBoundary
    protected Object doSciNumRational(SciNum left, Rational right) {
      return left.multiply(SciNum.fromBigInteger(right.denominator())).add(SciNum.fromBigInteger(right.numerator())).divide(SciNum.fromBigInteger(right.denominator()));
    }

    @Specialization
    protected Object typeError(Object left, Object right) {
      throw new TypeError("Cannot add " + left + " and " + right, this);
    }
  }

  @Specialization(guards = "left.unit() == right.unit()")
  protected Measure doMeasure(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoAddNode doAddNode) {
    return new Measure(doAddNode.executeAdd(frame, this, left.value(), right.value()), left.unit());
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasures(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoAddNode doAddNode) {
    return doAddNode.executeAdd(frame, this, left.value(), right.value());
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasureRight(VirtualFrame frame, Object left, Measure right,
      @Cached(inline = true) @Shared DoAddNode doAddNode) {
    return doAddNode.executeAdd(frame, this, left, right.value());
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasureLeft(VirtualFrame frame, Measure left, Object right,
      @Cached(inline = true) @Shared DoAddNode doAddNode) {
    return doAddNode.executeAdd(frame, this, left.value(), right);
  }

  @Specialization
  protected Object doOther(VirtualFrame frame, Object left, Object right,
      @Cached(inline = true) @Shared DoAddNode doAddNode) {
    return doAddNode.executeAdd(frame, this, left, right);
  }

  public static AddNode create(ValueNode leftNode, ValueNode rightNode, boolean isUntypedRegion, SourceSection sourceSection) {
    return AddNodeGen.create(leftNode, rightNode, isUntypedRegion, sourceSection);
  }
}
