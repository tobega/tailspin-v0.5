package tailspin.language.nodes.state;

import static tailspin.language.nodes.transform.TemplatesRootNode.DEFINING_SCOPE_ARG;

import com.oracle.truffle.api.dsl.Idempotent;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import tailspin.language.nodes.ValueNode;

@NodeField(name = "definitionLevel", type = int.class)
@NodeField(name = "stateSlot", type = int.class)
public abstract class GetStateNode extends ValueNode {
  @Idempotent
  protected abstract int getDefinitionLevel();
  protected abstract int getStateSlot();

  @Specialization(guards = "getDefinitionLevel() == 0")
  Object doLocal(VirtualFrame frame) {
    return frame.getObjectStatic(getStateSlot());
  }

  @Specialization
  @ExplodeLoop
  Object doUplevel(VirtualFrame frame) {
    Frame definingScope = frame;
    for (int i = 0; i < getDefinitionLevel(); i++)
      definingScope = (Frame) definingScope.getArguments()[DEFINING_SCOPE_ARG];
    return definingScope.getObjectStatic(getStateSlot());
  }

  public static GetStateNode create(int definitionLevel, int stateSlot) {
    return GetStateNodeGen.create(definitionLevel, stateSlot);
  }
}
