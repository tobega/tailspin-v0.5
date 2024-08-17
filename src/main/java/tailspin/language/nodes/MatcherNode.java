package tailspin.language.nodes;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.runtime.VocabularyType;

@TypeSystemReference(TailspinTypes.class)
public abstract class MatcherNode extends TailspinNode {
  public abstract boolean executeMatcherGeneric(VirtualFrame frame, Object toMatch);
  public abstract boolean executeMatcherLong(VirtualFrame frame, long toMatch);

  protected MatcherNode getTypeBound(Object value) {
    return VocabularyType.autoType(value);
  }
}
