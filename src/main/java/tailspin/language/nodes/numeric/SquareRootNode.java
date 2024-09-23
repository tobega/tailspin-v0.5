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
import tailspin.language.nodes.matchers.NumericTypeMatcherNode;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;

public abstract class SquareRootNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode squareNode;

  protected final boolean isUntypedRegion;

  SquareRootNode(ValueNode squareNode, boolean isUntypedRegion) {
    this.squareNode = squareNode;
    this.isUntypedRegion = isUntypedRegion;
  }

  @GenerateInline
  @TypeSystemReference(TailspinTypes.class)
  public static abstract class DoSquareRootNode extends Node {
    public abstract Object executeSquareRoot(VirtualFrame frame, Node node, Object square);

    @Specialization
    @TruffleBoundary
    protected SciNum doSciNum(SciNum square) {
      return square.squareRoot();
    }

    @Specialization
    @TruffleBoundary
    protected SciNum doRational(Rational square) {
      return SciNum.fromBigInteger(square.numerator()).squareRoot().divide(SciNum.fromBigInteger(square.denominator()).squareRoot());
    }

    @Specialization
    protected Object typeError(Object square) {
      throw new TypeError("Cannot take square root of " + square);
    }
  }

  @Specialization(guards = "isUntypedRegion")
  protected Object doUntypedMeasure(VirtualFrame frame, Measure square,
      @Cached(inline = true) @Shared DoSquareRootNode doSquareRootNode) {
    return doSquareRootNode.executeSquareRoot(frame, this, square.value());
  }

  @Specialization
  protected Object doOther(VirtualFrame frame, Object square,
      @Cached(inline = true) @Shared DoSquareRootNode doSquareRootNode) {
    return doSquareRootNode.executeSquareRoot(frame, this, square);
  }

  public static SquareRootNode create(ValueNode squareNode, boolean isUntypedRegion) {
    return SquareRootNodeGen.create(squareNode, isUntypedRegion);
  }

  @Override
  public MatcherNode getTypeMatcher() {
    return NumericTypeMatcherNode.create();
  }
}
