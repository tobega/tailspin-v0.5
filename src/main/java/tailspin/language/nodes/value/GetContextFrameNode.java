package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;

@GenerateInline
public abstract class GetContextFrameNode extends Node {

  public static final int DEFINING_SCOPE_ARG = 1;

  public abstract VirtualFrame execute(VirtualFrame frame, Node node, int level);

  @Specialization(guards = "level == 0")
  VirtualFrame doLocal(VirtualFrame frame, @SuppressWarnings("unused") int level) {
    return frame;
  }

  @Specialization(guards = "level > 0")
  @ExplodeLoop
  VirtualFrame doUplevel(VirtualFrame frame, int level) {
    VirtualFrame definingScope = frame;
    for (int i = 0; i < level; i++)
      definingScope = (VirtualFrame) definingScope.getArguments()[DEFINING_SCOPE_ARG];
    return definingScope;
  }

  @Fallback
  @SuppressWarnings("unused")
  VirtualFrame doNone(VirtualFrame frame, int level) {
    return null;
  }

  public static GetContextFrameNode create() {
    return GetContextFrameNodeGen.create();
  }
}
