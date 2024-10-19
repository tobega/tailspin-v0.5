package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
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
    return type.getConstraint().orElseThrow(() -> new TypeError(type + "is not defined"));
  }

  protected TagMatcherNode(VocabularyType type) {
    this.type = type;
  }

  static public TagMatcherNode create(VocabularyType key) {
    return TagMatcherNodeGen.create(key);
  }
}
