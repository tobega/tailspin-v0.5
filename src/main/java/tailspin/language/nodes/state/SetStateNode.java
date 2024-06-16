package tailspin.language.nodes.state;

import static tailspin.language.nodes.transform.TemplatesRootNode.DEFINING_SCOPE_ARG;

import com.oracle.truffle.api.dsl.Idempotent;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;

@NodeChild(value = "value", type = ValueNode.class)
@NodeField(name = "definitionLevel", type = int.class)
@NodeField(name = "stateSlot", type = int.class)
public abstract class SetStateNode extends StatementNode {
  @Idempotent
  protected abstract int getDefinitionLevel();
  protected abstract int getStateSlot();

  @Specialization(guards = "getDefinitionLevel() == 0")
  void doLocal(VirtualFrame frame, Object value) {
    frame.setObjectStatic(getStateSlot(), value);
  }

  @Specialization
  @ExplodeLoop
  void doUplevel(VirtualFrame frame, Object value) {
    Frame definingScope = frame;
    for (int i = 0; i < getDefinitionLevel(); i++)
      definingScope = (Frame) definingScope.getArguments()[DEFINING_SCOPE_ARG];
    definingScope.setObjectStatic(getStateSlot(), value);
  }

  public static SetStateNode create(ValueNode valueNode, int definitionLevel, int stateSlot) {
    return SetStateNodeGen.create(valueNode, definitionLevel, stateSlot);
  }
}
