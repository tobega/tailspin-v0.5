package tailspin.language.nodes.transform;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.GetContextFrameNode;
import tailspin.language.nodes.value.LocalReferenceNode;
import tailspin.language.runtime.Templates;

public abstract class SendToTemplatesNode extends ValueNode {

  @Child @Executed
  @SuppressWarnings("FieldMayBeFinal")
  protected ValueNode valueNode;

  private final Templates templates;
  protected final int definitionLevel;

  protected SendToTemplatesNode(int chainCvSlot, Templates templates, int definitionLevel) {
    this.valueNode = LocalReferenceNode.create(chainCvSlot);
    this.definitionLevel = definitionLevel;
    this.templates = templates;
  }

  public static SendToTemplatesNode create(int chainCvSlot, Templates templates, int definitionLevel) {
    return SendToTemplatesNodeGen.create(chainCvSlot, templates, definitionLevel);
  }

  @Specialization(guards = "definitionLevel >= 0")
  public Object doDispatch(VirtualFrame frame, Object value,
      @Cached(inline = true) GetContextFrameNode getContextFrameNode,
      @Cached @Shared DispatchNode dispatchNode) {
    VirtualFrame contextFrame = getContextFrameNode.execute(frame, this, definitionLevel);
    return dispatchNode.executeDispatch(templates, value, contextFrame.materialize());
  }

  @Specialization(guards = "definitionLevel < 0")
  public Object doFree(@SuppressWarnings("unused") VirtualFrame frame, Object value,
      @Cached @Shared DispatchNode dispatchNode) {
    return dispatchNode.executeDispatch(templates, value, null);
  }

  @GenerateInline(value = false)
  public static abstract class DispatchNode extends Node {
    public abstract Object executeDispatch(Templates templates, Object currentValue, Frame definingScope);
    @Specialization
    protected static Object dispatchDirectly(
        @SuppressWarnings("unused") Templates templates,
        Object currentValue,
        Frame definingScope,
        @Cached("create(templates.getCallTarget())") DirectCallNode directCallNode) {
      return directCallNode.call(currentValue, definingScope, null);
    }
  }
}
