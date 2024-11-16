package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.TailspinArray;

public abstract class ArrayContentMatcherNode extends MatcherNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  MatcherNode contentMatcher;

  ArrayContentMatcherNode(MatcherNode contentMatcher, SourceSection sourceSection) {
    super(sourceSection);
    this.contentMatcher = contentMatcher;
  }

  @Specialization
  boolean findElement(VirtualFrame frame, TailspinArray array) {
    for (int i = 0; i < array.getArraySize(); i++) {
      if (contentMatcher.executeMatcherGeneric(frame, array.getNative(i, false))) return true;
    }
    return false;
  }

  public static ArrayContentMatcherNode create(MatcherNode elementMatcher,
      SourceSection sourceSection) {
    return ArrayContentMatcherNodeGen.create(elementMatcher, sourceSection);
  }
}
