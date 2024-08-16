package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.TypeError;
import tailspin.language.nodes.TailspinTypes;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.SciNum;

@NodeChild("leftNode") @NodeChild("rightNode")
public abstract class DivideNode extends ValueNode {

  @GenerateInline
  @TypeSystemReference(TailspinTypes.class)
  public static abstract class DoDivideNode extends Node {
    public abstract Object executeDivide(VirtualFrame frame, Node node, Object left, Object right);

    @Specialization
    @TruffleBoundary
    protected SciNum doSciNum(SciNum left, SciNum right) {
      return left.divide(right);
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

  @Specialization
  protected Object doUntyped(VirtualFrame frame, Object left, Object right,
      @Cached(inline = true) @Shared DoDivideNode doDivideNode) {
    return doDivideNode.executeDivide(frame, this, left, right);
  }

  public static DivideNode create(ValueNode leftNode, ValueNode rightNode) {
    return DivideNodeGen.create(leftNode, rightNode);
  }
}
