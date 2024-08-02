package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.Structure;

@GenerateInline(false)
public abstract class StructureTypeMatcherNode extends MatcherNode {

  @Specialization
  protected boolean isStructure(Structure ignored) {
    return true;
  }

  @Fallback
  protected boolean notStructure(Object ignored) { return false; }

  public static StructureTypeMatcherNode create() {
    return StructureTypeMatcherNodeGen.create();
  }
}
