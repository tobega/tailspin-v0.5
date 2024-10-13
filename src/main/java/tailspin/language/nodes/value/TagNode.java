package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.TypeError;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.VocabularyType;

@NodeChild("value")
public abstract class TagNode extends ValueNode {
  final VocabularyType type;

  protected TagNode(VocabularyType type) {
    this.type = type;
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
