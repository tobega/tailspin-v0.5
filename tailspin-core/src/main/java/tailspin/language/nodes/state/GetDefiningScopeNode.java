package tailspin.language.nodes.state;

import static tailspin.language.runtime.Templates.SCOPE_SLOT;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.runtime.DefiningScope;

@GenerateInline
public abstract class GetDefiningScopeNode extends Node {

  public abstract DefiningScope execute(VirtualFrame frame, Node node, int level);

  @Specialization(guards = "level >= 0")
  @ExplodeLoop
  DefiningScope doUplevel(VirtualFrame frame, int level) {
    DefiningScope definingScope = (DefiningScope) frame.getObjectStatic(SCOPE_SLOT);
    for (int i = 0; i < level; i++)
      definingScope = definingScope.getParentScope();
    return definingScope;
  }

  @Fallback
  @SuppressWarnings("unused")
  DefiningScope doNone(VirtualFrame frame, int level) {
    return null;
  }

  public static GetDefiningScopeNode create() {
    return GetDefiningScopeNodeGen.create();
  }
}
