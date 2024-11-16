package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.TypeError;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.TaggedValue;
import tailspin.language.runtime.VocabularyType;

@NodeChild(value = "value", type = ValueNode.class)
public abstract class TagNode extends ValueNode {
  public abstract Object executeDirect(VirtualFrame frame, Object value);

  final VocabularyType type;

  protected TagNode(VocabularyType type, SourceSection sourceSection) {
    super(sourceSection);
    this.type = type;
  }

  @Specialization(guards = "type == value.type()")
  TaggedValue doAlreadyTagged(VirtualFrame ignored, TaggedValue value) {
    return value;
  }

  @Specialization
  TaggedValue doTagLong(VirtualFrame frame, long value,
      @Cached(value = "type.getConstraint(value)", neverDefault = true) MatcherNode typeConstraint) {
    if (typeConstraint.executeMatcherGeneric(frame, value)) {
      return new TaggedValue(type, value);
    }
    throw new TypeError(value + " is not of type " + type.toString(), this);
  }

  @Specialization
  TaggedValue doTagBigNumber(VirtualFrame frame, BigNumber value,
      @Cached(value = "type.getConstraint(value)", neverDefault = true) MatcherNode typeConstraint) {
    if (typeConstraint.executeMatcherGeneric(frame, value)) {
      return new TaggedValue(type, value);
    }
    throw new TypeError(value + " is not of type " + type.toString(), this);
  }

  @Specialization
  TaggedValue doTagRational(VirtualFrame frame, Rational value,
      @Cached(value = "type.getConstraint(value)", neverDefault = true) MatcherNode typeConstraint) {
    if (typeConstraint.executeMatcherGeneric(frame, value)) {
      return new TaggedValue(type, value);
    }
    throw new TypeError(value + " is not of type " + type.toString(), this);
  }

  @Specialization
  TaggedValue doTagSciNum(VirtualFrame frame, SciNum value,
      @Cached(value = "type.getConstraint(value)", neverDefault = true) MatcherNode typeConstraint) {
    if (typeConstraint.executeMatcherGeneric(frame, value)) {
      return new TaggedValue(type, value);
    }
    throw new TypeError(value + " is not of type " + type.toString(), this);
  }

  @Specialization
  Object doTypeCheck(VirtualFrame frame, Object value,
      @Cached(value = "type.getConstraint(value)", neverDefault = true) MatcherNode typeConstraint) {
    if (typeConstraint.executeMatcherGeneric(frame, value)) {
      return value;
    }
    throw new TypeError(value.toString() + " is not of type " + type.toString(), this);
  }

  public static TagNode create(VocabularyType type, ValueNode valueNode,
      SourceSection sourceSection) {
    return TagNodeGen.create(type, sourceSection, valueNode);
  }
}
