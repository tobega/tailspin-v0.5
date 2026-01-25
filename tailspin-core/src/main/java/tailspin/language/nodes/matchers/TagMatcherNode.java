package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.TypeError;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.TaggedValue;
import tailspin.language.runtime.VocabularyType;

public abstract class TagMatcherNode extends MatcherNode {
  final VocabularyType type;

  @Specialization(guards = "value.type() == type")
  boolean doSameTag(TaggedValue value) {
    return true;
  }

  @Fallback
  boolean doOther(VirtualFrame frame, Object value,
      @Cached(value = "getConstraint()", neverDefault = true) MatcherNode matcherNode) {
    return matcherNode.executeMatcherGeneric(frame, value);
  }

  MatcherNode getConstraint() {
    return type.getConstraint().orElseThrow(() -> new TypeError(type + "is not defined", this));
  }

  protected TagMatcherNode(VocabularyType type, SourceSection sourceSection) {
    super(sourceSection);
    this.type = type;
  }

  static public TagMatcherNode create(VocabularyType key, SourceSection sourceSection) {
    return TagMatcherNodeGen.create(key, sourceSection);
  }
}
