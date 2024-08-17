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
import tailspin.language.runtime.SciNum;

public abstract class SubtractNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode leftNode;

  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode rightNode;

  SubtractNode(ValueNode leftNode, ValueNode rightNode) {
    this.leftNode = leftNode;
    this.rightNode = rightNode;
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
    protected SciNum doSciNum(SciNum left, SciNum right) {
      return left.subtract(right);
    }

    @Specialization
    protected Object typeError(Object left, Object right) {
      throw new TypeError("Cannot subtract " + left + " and " + right);
    }
  }

  @Specialization(guards = "left.unit() == right.unit()")
  protected Measure doMeasure(VirtualFrame frame, Measure left, Measure right,
      @Cached(inline = true) @Shared DoSubtractNode doSubtractNode) {
    return new Measure(doSubtractNode.executeSubtract(frame, this, left.value(), right.value()), left.unit());
  }

  @Specialization
  protected Object doUntyped(VirtualFrame frame, Object left, Object right,
      @Cached(inline = true) @Shared DoSubtractNode doSubtractNode) {
    return doSubtractNode.executeSubtract(frame, this, left, right);
  }

  public static SubtractNode create(ValueNode leftNode, ValueNode rightNode) {
    return SubtractNodeGen.create(leftNode, rightNode);
  }

  @Override
  public MatcherNode getTypeMatcher() {
    MatcherNode typeMatcher = leftNode.getTypeMatcher();
    if (typeMatcher == null) typeMatcher = rightNode.getTypeMatcher();
    return typeMatcher;
  }
}
