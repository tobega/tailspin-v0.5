package tailspin.language.nodes.state;

import static tailspin.language.runtime.Templates.SCOPE_SLOT;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.nodes.transform.TemplatesRootNode;
import tailspin.language.nodes.value.GetContextFrameNodeGen;

@GenerateInline
public abstract class GetStateFrameNode extends Node {

  public abstract VirtualFrame execute(VirtualFrame frame, Node node, int level);

  @Specialization(guards = "level < 0")
  VirtualFrame doLocal(VirtualFrame frame, @SuppressWarnings("unused") int level) {
    return frame;
  }

  @Specialization(guards = "level >= 0")
  @ExplodeLoop
  VirtualFrame doUplevel(VirtualFrame frame, int level) {
    VirtualFrame definingScope = (VirtualFrame) frame.getObjectStatic(SCOPE_SLOT);
    for (int i = 0; i < level; i++)
      definingScope = (VirtualFrame) definingScope.getArguments()[TemplatesRootNode.DEFINING_SCOPE_ARG];
    return definingScope;
  }

  @Fallback
  @SuppressWarnings("unused")
  VirtualFrame doNone(VirtualFrame frame, int level) {
    return null;
  }

  public static GetStateFrameNode create() {
    return GetStateFrameNodeGen.create();
  }
}
