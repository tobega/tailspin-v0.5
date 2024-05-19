package tailspin.language.nodes.value;

import static tailspin.language.nodes.transform.SendToTemplatesNode.DEFINING_SCOPE_ARG;

import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;

@GenerateInline
public abstract class GetContextFrameNode extends Node {
  public abstract VirtualFrame execute(VirtualFrame frame, Node node, int level);

  @Specialization(guards = "level == 0")
  VirtualFrame doLocal(VirtualFrame frame, @SuppressWarnings("unused") int level) {
    return frame;
  }

  @Specialization
  @ExplodeLoop
  VirtualFrame doUplevel(VirtualFrame frame, int level) {
    VirtualFrame definingScope = frame;
    for (int i = 0; i < level; i++)
      definingScope = (VirtualFrame) definingScope.getArguments()[DEFINING_SCOPE_ARG];
    return definingScope;
  }

  public static GetContextFrameNode create() {
    return GetContextFrameNodeGen.create();
  }
}
