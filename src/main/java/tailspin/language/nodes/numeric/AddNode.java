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
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.SciNum;

@NodeChild("leftNode") @NodeChild("rightNode")
public abstract class AddNode extends ValueNode {

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
    protected SciNum doSciNum(SciNum left, SciNum right) {
      return left.add(right);
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
}
