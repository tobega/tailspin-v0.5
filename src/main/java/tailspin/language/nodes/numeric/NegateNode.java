package tailspin.language.nodes.numeric;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.TypeError;
import tailspin.language.nodes.TailspinTypes;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;

@NodeChild(type = ValueNode.class)
public abstract class NegateNode extends ValueNode {

  public NegateNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  @GenerateInline
  @TypeSystemReference(TailspinTypes.class)
  public static abstract class DoNegateNode extends Node {

    public abstract Object executeNegate(VirtualFrame frame, Node node, Object value);

    @Specialization(rewriteOn = ArithmeticException.class)
    long doLong(long value) {
      return Math.negateExact(value);
    }

    @Specialization
    @TruffleBoundary
    protected BigNumber doBigNumber(BigNumber value) {
      return value.negate();
    }

    @Specialization
    @TruffleBoundary
    protected Object doRational(Rational value) {
      return value.negate();
    }

    @Specialization
    @TruffleBoundary
    protected SciNum doSciNum(SciNum value) {
      return value.negate();
    }

    @Specialization
    protected Object typeError(Object value) {
      throw new TypeError("Cannot negate " + value, this);
    }
  }

  @Specialization
  protected Measure doMeasure(VirtualFrame frame, Measure value,
      @Cached(inline = true) @Shared DoNegateNode doNegateNode) {
    return new Measure(doNegateNode.executeNegate(frame, this, value.value()), value.unit());
  }

  @Specialization
  protected Object doUntyped(VirtualFrame frame, Object value,
      @Cached(inline = true) @Shared DoNegateNode doNegateNode) {
    return doNegateNode.executeNegate(frame, this, value);
  }

  public static NegateNode create(ValueNode valueNode) {
    return NegateNodeGen.create(INTERNAL_CODE_SOURCE, valueNode);
  }
}
