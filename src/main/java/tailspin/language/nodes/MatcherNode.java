package tailspin.language.nodes;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.runtime.VocabularyType;

@TypeSystemReference(TailspinTypes.class)
public abstract class MatcherNode extends TailspinNode {

  protected MatcherNode(MatcherNode typeBound) {
    this.typeBound = typeBound;
  }

  public abstract boolean executeMatcherGeneric(VirtualFrame frame, Object toMatch);
  public abstract boolean executeMatcherLong(VirtualFrame frame, long toMatch);

  private final MatcherNode typeBound;

  protected MatcherNode getTypeBound(Object value) {
    if (typeBound != null) return typeBound;
    return VocabularyType.autoType(value);
  }
}
