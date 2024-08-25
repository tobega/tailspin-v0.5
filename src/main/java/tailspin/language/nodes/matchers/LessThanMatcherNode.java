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
public abstract class LessThanMatcherNode extends MatcherNode {
  public abstract boolean executeComparison(VirtualFrame frame, Object toMatch, Object value);

  private final boolean inclusive;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private MatcherNode typeBound;

  protected LessThanMatcherNode(boolean inclusive, MatcherNode typeBound) {
    this.inclusive = inclusive;
    this.typeBound = typeBound;
  }

  @GenerateInline
  @TypeSystemReference(TailspinTypes.class)
  public static abstract class DoLessThanNode extends Node {
    public abstract boolean executeLessThan(VirtualFrame frame, Node node, Object toMatch, Object required, boolean inclusive);

    @Specialization
    protected boolean longLess(long toMatch, long value, boolean inclusive) {
      return toMatch < value || inclusive && toMatch == value;
    }

    @Specialization(replaces = "longLess")
    @TruffleBoundary
    protected boolean bigNumberLess(BigNumber toMatch, BigNumber value, boolean inclusive) {
      return toMatch.compareTo(value) <= (inclusive ? 0 : -1);
    }

    @Specialization
    @TruffleBoundary
    protected boolean rationalLess(Rational toMatch, Rational value, boolean inclusive) {
      return toMatch.compareTo(value) <= (inclusive ? 0 : -1);
    }

    @Specialization
    @TruffleBoundary
    protected boolean doRationalSciNum(Rational toMatch, SciNum value, boolean inclusive) {
      return value.compareTo(toMatch) >= (inclusive ? 0 : 1);
    }

    @Specialization
    @TruffleBoundary
    protected boolean doSciNumRational(SciNum toMatch, Rational value, boolean inclusive) {
      return toMatch.compareTo(value) <= (inclusive ? 0 : -1);
    }

    @Specialization
    @TruffleBoundary
    protected boolean sciNumLess(SciNum toMatch, SciNum value, boolean inclusive) {
      return toMatch.compareTo(value) <= (inclusive ? 0 : -1);
    }

    @Fallback
    @SuppressWarnings("unused")
    protected boolean objectLess(Object toMatch, Object value, boolean ignored) {
      return false;
    }
  }

  @Specialization(guards = "toMatch.unit() == value.unit()")
  protected boolean doMeasure(VirtualFrame frame, Measure toMatch, Measure value,
      @Cached(inline = true) @Shared DoLessThanNode doLessThanNode) {
    return doLessThanNode.executeLessThan(frame, this, toMatch.value(), value.value(), inclusive);
  }

  @Specialization(guards = "hasStaticTypeBound()")
  protected boolean doStaticBound(VirtualFrame frame, Object toMatch, Object value,
      @Cached(inline = true) @Shared DoLessThanNode doLessThanNode) {
    if (doLessThanNode.executeLessThan(frame, this, toMatch, value, inclusive)) return true;
    if (typeBound.executeMatcherGeneric(frame, toMatch)) return false;
    throw new TypeError("Incompatible type comparison " + toMatch + " <= " + value);
  }

  @Idempotent
  boolean hasStaticTypeBound() {
    return typeBound != null;
  }

  @Specialization(guards = {"!hasStaticTypeBound()", "dynamicBound.executeMatcherGeneric(frame, value)"}, limit = "3")
  protected boolean doDynamicCachedBound(VirtualFrame frame, Object toMatch, Object value,
      @Cached(inline = true) @Shared DoLessThanNode doLessThanNode,
      @Cached("autoType(value)") MatcherNode dynamicBound) {
    if (doLessThanNode.executeLessThan(frame, this, toMatch, value, inclusive)) return true;
    if (dynamicBound.executeMatcherGeneric(frame, toMatch)) return false;
    throw new TypeError("Incompatible type comparison " + toMatch + " <= " + value);
  }

  MatcherNode autoType(Object value) {
    return VocabularyType.autoType(value);
  }

  @Specialization(guards = "!hasStaticTypeBound()")
  protected boolean doDynamicBound(VirtualFrame frame, Object toMatch, Object value,
      @Cached(inline = true) @Shared DoLessThanNode doLessThanNode) {
    if (doLessThanNode.executeLessThan(frame, this, toMatch, value, inclusive)) return true;
    MatcherNode dynamicBound = autoType(value);
    if (dynamicBound.executeMatcherGeneric(frame, toMatch)) return false;
    throw new TypeError("Incompatible type comparison " + toMatch + " <= " + value);
  }

  public static LessThanMatcherNode create(boolean inclusive, MatcherNode typeBound, ValueNode valueNode) {
    if (typeBound == null) typeBound = valueNode.getTypeMatcher();
    return LessThanMatcherNodeGen.create(inclusive, typeBound, null, valueNode);
  }
}
