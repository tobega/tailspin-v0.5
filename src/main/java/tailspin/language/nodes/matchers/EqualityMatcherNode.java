package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Exclusive;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import tailspin.language.TypeError;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.TailspinTypes;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.SmallSciNum;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.TaggedValue;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.VocabularyType;

@NodeChild(value = "dummy", type = ValueNode.class)
@NodeChild(value = "valueNode", type = ValueNode.class)
public abstract class EqualityMatcherNode extends MatcherNode {
  protected final boolean isTypeChecked;

  protected EqualityMatcherNode(boolean isTypeChecked, SourceSection sourceSection) {
    super(sourceSection);
    this.isTypeChecked = isTypeChecked;
  }

  @GenerateInline
  @TypeSystemReference(TailspinTypes.class)
  public static abstract class DoEqualityNode extends Node {
    public abstract boolean executeEquals(Node node, Object toMatch, Object required);

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
    protected boolean doSmallSciNum(SmallSciNum left, SmallSciNum right) {
      return left.compareTo(right) == 0;
    }

    @Specialization
    @TruffleBoundary
    protected boolean doSmallSciNumLong(SmallSciNum left, Long right) {
      return left.compareTo(SmallSciNum.fromLong(right)) == 0;
    }

    @Specialization
    @TruffleBoundary
    protected boolean doLongSmallSciNum(Long left, SmallSciNum right) {
      return SmallSciNum.fromLong(left).compareTo(right) == 0;
    }

    @Specialization
    @TruffleBoundary
    protected boolean doSciNum(SciNum left, SciNum right) {
      return left.compareTo(right) == 0;
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
    protected boolean doMeasure(Node node, Measure left, Measure right,
        @Cached(inline = false) @Shared DoEqualityNode doEqualityNode) {
      return left.unit() == right.unit() && doEqualityNode.executeEquals(node, left.value(), right.value());
    }

    @Specialization
    protected boolean doTaggedValue(Node node, TaggedValue left, TaggedValue right,
        @Cached(inline = false) @Shared DoEqualityNode doEqualityNode) {
      return left.type() == right.type() && doEqualityNode.executeEquals(node, left.value(), right.value());
    }

    @Specialization
    protected boolean doString(TruffleString left, TruffleString right) {
      // Note: this requires same encoding to work
      return left.equals(right);
    }

    @Specialization
    protected boolean doArray(Node node, TailspinArray left, TailspinArray right,
        @Cached(inline = false) @Exclusive DoEqualityNode indexEqualityNode,
        @Cached(inline = false) @Exclusive DoEqualityNode sizeEqualityNode,
        @Cached(inline = false) @Shared DoEqualityNode doEqualityNode) {
      if (!indexEqualityNode.executeEquals(node, left.first(), right.first())) return false;
      if (!sizeEqualityNode.executeEquals(node, left.getArraySize(), right.getArraySize())) return false;
      for (int i = 0; i < left.getArraySize(); i++) {
        if (!doEqualityNode.executeEquals(node, left.getNative(i, false), right.getNative(i, false))) return false;
      }
      return true;
    }

    @Specialization
    protected boolean doStructure(Node node, Structure left, Structure right,
        @CachedLibrary(limit = "2") DynamicObjectLibrary leftLibrary,
        @CachedLibrary(limit = "2") DynamicObjectLibrary rightLibrary,
        @Cached(inline = false) @Shared DoEqualityNode doEqualityNode) {
      Object[] rightKeys = rightLibrary.getKeyArray(right);
      if (leftLibrary.getKeyArray(left).length != rightKeys.length) return false;
      for (Object key : rightKeys) {
        if (!doEqualityNode.executeEquals(node, leftLibrary.getOrDefault(left, key, null), rightLibrary.getOrDefault(right, key, null))) return false;
      }
      return true;
    }

    @Fallback
    protected boolean objectEquals(Object toMatch, Object value) {
      return false;
    }
  }

  @Specialization(guards = "isTypeChecked")
  protected boolean doStaticBound(VirtualFrame frame, Object toMatch, Object value,
      @Cached(inline = true) @Shared DoEqualityNode doEqualityNode) {
    return doEqualityNode.executeEquals(this, toMatch, value);
  }

  @Specialization(guards = {"!isTypeChecked", "dynamicBound.executeMatcherGeneric(frame, value)"}, limit = "3")
  protected boolean doDynamicCachedBound(VirtualFrame frame, Object toMatch, Object value,
      @Cached(inline = true) @Shared DoEqualityNode doEqualityNode,
      @Cached("autoType(value)") MatcherNode dynamicBound) {
    if (doEqualityNode.executeEquals(this, toMatch, value)) return true;
    if (dynamicBound.executeMatcherGeneric(frame, toMatch)) return false;
    throw new TypeError("Incompatible type comparison " + toMatch + " = " + value, this);
  }

  MatcherNode autoType(Object value) {
    return VocabularyType.autoType(value);
  }

  @Specialization(guards = "!isTypeChecked")
  protected boolean doDynamicBound(VirtualFrame frame, Object toMatch, Object value,
      @Cached(inline = true) @Shared DoEqualityNode doEqualityNode) {
    if (doEqualityNode.executeEquals(this, toMatch, value)) return true;
    MatcherNode dynamicBound = autoType(value);
    if (dynamicBound.executeMatcherGeneric(frame, toMatch)) return false;
    throw new TypeError("Incompatible type comparison " + toMatch + " = " + value, this);
  }

  public static EqualityMatcherNode create(boolean isTypeChecked, ValueNode valueNode,
      SourceSection sourceSection) {
    return EqualityMatcherNodeGen.create(isTypeChecked, sourceSection, null, valueNode);
  }
}
