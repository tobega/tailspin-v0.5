package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Idempotent;
import com.oracle.truffle.api.dsl.NodeChild;
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
import tailspin.language.runtime.VocabularyType;

@NodeChild(value = "dummy", type = ValueNode.class)
@NodeChild(value = "valueNode", type = ValueNode.class)
public abstract class EqualityMatcherNode extends MatcherNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private MatcherNode typeBound;

  protected EqualityMatcherNode(MatcherNode typeBound) {
    this.typeBound = typeBound;
  }

  @GenerateInline
  @TypeSystemReference(TailspinTypes.class)
  public static abstract class DoEqualityNode extends Node {
    public abstract boolean executeEquals(VirtualFrame frame, Node node, Object toMatch, Object required);

    @Specialization
    protected boolean longEquals(long toMatch, long value) {
      return toMatch == value;
    }

    @Specialization(replaces = "longEquals")
    @TruffleBoundary
    protected boolean bigNumberEquals(BigNumber toMatch, BigNumber value) {
      return toMatch.equals(value);
    }

    @Specialization
    @TruffleBoundary
    protected boolean rationalEquals(Rational toMatch, Rational value) {
      return toMatch.equals(value);
    }

    @Specialization
    @TruffleBoundary
    protected boolean doRationalSciNum(Rational left, SciNum right) {
      return right.compareTo(left) == 0;
    }

    @Specialization
    @TruffleBoundary
    protected boolean doSciNumRational(SciNum left, Rational right) {
      return left.compareTo(right) == 0;
    }

    @Specialization
    @TruffleBoundary
    protected boolean doSciNum(SciNum left, SciNum right) {
      return left.compareTo(right) == 0;
    }

    @Fallback
    protected boolean objectEquals(Object toMatch, Object value) {
      return toMatch.equals(value);
    }
  }

  @Specialization(guards = "toMatch.unit() == value.unit()")
  protected boolean doMeasure(VirtualFrame frame, Measure toMatch, Measure value,
      @Cached(inline = true) @Shared DoEqualityNode doEqualityNode) {
    return doEqualityNode.executeEquals(frame, this, toMatch.value(), value.value());
  }

  @Specialization(guards = "hasStaticTypeBound()")
  protected boolean doStaticBound(VirtualFrame frame, Object toMatch, Object value,
      @Cached(inline = true) @Shared DoEqualityNode doEqualityNode) {
    if (doEqualityNode.executeEquals(frame, this, toMatch, value)) return true;
    if (typeBound.executeMatcherGeneric(frame, toMatch)) return false;
    throw new TypeError("Incompatible type comparison " + toMatch + " = " + value);
  }

  @Idempotent
  boolean hasStaticTypeBound() {
    return typeBound != null;
  }

  @Specialization(guards = {"!hasStaticTypeBound()", "dynamicBound.executeMatcherGeneric(frame, value)"}, limit = "3")
  protected boolean doDynamicCachedBound(VirtualFrame frame, Object toMatch, Object value,
      @Cached(inline = true) @Shared DoEqualityNode doEqualityNode,
      @Cached("autoType(value)") MatcherNode dynamicBound) {
    if (doEqualityNode.executeEquals(frame, this, toMatch, value)) return true;
    if (dynamicBound.executeMatcherGeneric(frame, toMatch)) return false;
    throw new TypeError("Incompatible type comparison " + toMatch + " = " + value);
  }

  MatcherNode autoType(Object value) {
    return VocabularyType.autoType(value);
  }

  @Specialization(guards = "!hasStaticTypeBound()")
  protected boolean doDynamicBound(VirtualFrame frame, Object toMatch, Object value,
      @Cached(inline = true) @Shared DoEqualityNode doEqualityNode) {
    if (doEqualityNode.executeEquals(frame, this, toMatch, value)) return true;
    MatcherNode dynamicBound = autoType(value);
    if (dynamicBound.executeMatcherGeneric(frame, toMatch)) return false;
    throw new TypeError("Incompatible type comparison " + toMatch + " = " + value);
  }

  public static EqualityMatcherNode create(MatcherNode typeBound, ValueNode valueNode) {
    if (typeBound == null) typeBound = valueNode.getTypeMatcher();
    return EqualityMatcherNodeGen.create(typeBound, null, valueNode);
  }
}
