package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Cached;
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
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;

public abstract class SquareRootNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode squareNode;

  SquareRootNode(ValueNode squareNode) {
    this.squareNode = squareNode;
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

  @Specialization
  protected Object doUntyped(VirtualFrame frame, Object square,
      @Cached(inline = true) DoSquareRootNode doSquareRootNode) {
    return doSquareRootNode.executeSquareRoot(frame, this, square);
  }

  public static SquareRootNode create(ValueNode squareNode) {
    return SquareRootNodeGen.create(squareNode);
  }

  @Override
  public MatcherNode getTypeMatcher() {
    return NumericTypeMatcherNode.create();
  }
}
