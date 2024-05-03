package tailspin.language.nodes.transform;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import tailspin.language.nodes.DispatchNode;
import tailspin.language.nodes.DispatchNodeGen;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.LocalReferenceNode;
import tailspin.language.runtime.Templates;

public abstract class SendToTemplatesNode extends ValueNode {
  public static final int DEFINING_SCOPE_ARG = 1;

  @Child @Executed
  @SuppressWarnings("FieldMayBeFinal")
  protected ValueNode valueNode;

  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private DispatchNode dispatchNode;

  private final Templates templates;
  protected final int definitionLevel;

  protected SendToTemplatesNode(int chainCvSlot, Templates templates, int definitionLevel) {
    this.valueNode = LocalReferenceNode.create(chainCvSlot);
    this.definitionLevel = definitionLevel;
    this.dispatchNode = DispatchNodeGen.create();
    this.templates = templates;
  }

  public static SendToTemplatesNode create(int chainCvSlot, Templates templates, int definitionLevel) {
    return SendToTemplatesNodeGen.create(chainCvSlot, templates, definitionLevel);
  }

  @Specialization(guards = "definitionLevel < 0")
  public Object doFree(@SuppressWarnings("unused") VirtualFrame frame, Object value) {
    return dispatchNode.executeDispatch(templates, value, null);
  }

  @Specialization(guards = "definitionLevel == 0")
  public Object doLocal(VirtualFrame frame, Object value) {
    return dispatchNode.executeDispatch(templates, value, frame.materialize());
  }

  @Specialization
  @ExplodeLoop
  public Object doUplevel(VirtualFrame frame, Object value) {
    Frame definingScope = frame;
    for (int i = 0; i < definitionLevel; i++)
      definingScope = (Frame) definingScope.getArguments()[DEFINING_SCOPE_ARG];
    return dispatchNode.executeDispatch(templates, value, definingScope);
  }
}
