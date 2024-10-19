package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.TypeError;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.TaggedValue;
import tailspin.language.runtime.VocabularyType;

public abstract class TagNode extends ValueNode {
  final VocabularyType type;
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  protected ValueNode valueNode;

  protected TagNode(VocabularyType type, ValueNode valueNode) {
    this.type = type;
    this.valueNode = valueNode;
  }

  @Specialization
  TaggedValue doTagLong(VirtualFrame frame, long value,
      @Cached(value = "type.getConstraint(value)", neverDefault = true) MatcherNode typeConstraint) {
    if (typeConstraint.executeMatcherGeneric(frame, value)) {
      return new TaggedValue(type, value);
    }
    throw new TypeError(value + " is not of type " + type.toString());
  }

  @Specialization
  TaggedValue doTagBigNumber(VirtualFrame frame, BigNumber value,
      @Cached(value = "type.getConstraint(value)", neverDefault = true) MatcherNode typeConstraint) {
    if (typeConstraint.executeMatcherGeneric(frame, value)) {
      return new TaggedValue(type, value);
    }
    throw new TypeError(value + " is not of type " + type.toString());
  }

  @Specialization
  TaggedValue doTagRational(VirtualFrame frame, Rational value,
      @Cached(value = "type.getConstraint(value)", neverDefault = true) MatcherNode typeConstraint) {
    if (typeConstraint.executeMatcherGeneric(frame, value)) {
      return new TaggedValue(type, value);
    }
    throw new TypeError(value + " is not of type " + type.toString());
  }

  @Specialization
  TaggedValue doTagSciNum(VirtualFrame frame, SciNum value,
      @Cached(value = "type.getConstraint(value)", neverDefault = true) MatcherNode typeConstraint) {
    if (typeConstraint.executeMatcherGeneric(frame, value)) {
      return new TaggedValue(type, value);
    }
    throw new TypeError(value + " is not of type " + type.toString());
  }

  @Specialization
  Object doTypeCheck(VirtualFrame frame, Object value,
      @Cached(value = "type.getConstraint(value)", neverDefault = true) MatcherNode typeConstraint) {
    if (typeConstraint.executeMatcherGeneric(frame, value)) {
      return value;
    }
    throw new TypeError(value.toString() + " is not of type " + type.toString());
  }

  public static TagNode create(VocabularyType type, ValueNode valueNode) {
    return TagNodeGen.create(type, valueNode);
  }
}
