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

public abstract class AddNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode leftNode;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode rightNode;

  AddNode(ValueNode leftNode, ValueNode rightNode) {
    this.leftNode = leftNode;
    this.rightNode = rightNode;
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
    protected SciNum doSciNum(SciNum left, SciNum right) {
      return left.add(right);
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
      throw new TypeError("Cannot add " + left + " and " + right);
    }
  }

  @Specialization(guards = "left.unit() == right.unit()")
  protected Measure doMeasure(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoAddNode doAddNode) {
    return new Measure(doAddNode.executeAdd(frame, this, left.value(), right.value()), left.unit());
  }

  @Specialization
  protected Object doUntyped(VirtualFrame frame, Object left, Object right,
      @Cached(inline = true) @Shared DoAddNode doAddNode) {
    return doAddNode.executeAdd(frame, this, left, right);
  }

  public static AddNode create(ValueNode leftNode, ValueNode rightNode) {
    return AddNodeGen.create(leftNode, rightNode);
  }

  @Override
  public MatcherNode getTypeMatcher() {
    MatcherNode typeMatcher = leftNode.getTypeMatcher();
    if (typeMatcher == null) typeMatcher = rightNode.getTypeMatcher();
    return typeMatcher;
  }
}
