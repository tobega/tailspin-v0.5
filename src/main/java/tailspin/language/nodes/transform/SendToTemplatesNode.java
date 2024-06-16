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
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.GetContextFrameNode;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.runtime.Templates;

public abstract class SendToTemplatesNode extends TransformNode {

  @Child @Executed
  @SuppressWarnings("FieldMayBeFinal")
  protected ValueNode valueNode;

  private final Templates templates;
  protected final int definitionLevel;

  protected SendToTemplatesNode(int chainCvSlot, Templates templates, int definitionLevel) {
    this.valueNode = ReadContextValueNode.create(0, chainCvSlot);
    this.definitionLevel = definitionLevel;
    this.templates = templates;
  }

  public static SendToTemplatesNode create(int chainCvSlot, Templates templates, int definitionLevel) {
    return SendToTemplatesNodeGen.create(chainCvSlot, templates, definitionLevel);
  }

  @Specialization(guards = "definitionLevel >= 0")
  public void doDispatch(VirtualFrame frame, Object value,
      @Cached(inline = true) GetContextFrameNode getContextFrameNode,
      @Cached @Shared DispatchNode dispatchNode) {
    VirtualFrame contextFrame = getContextFrameNode.execute(frame, this, definitionLevel);
    Object resultBuilder = frame.getObjectStatic(getResultSlot());
    Object result = dispatchNode.executeDispatch(templates, value, contextFrame.materialize(),
        resultBuilder);
    frame.setObjectStatic(getResultSlot(), result);
  }

  @Specialization(guards = "definitionLevel < 0")
  public void doFree(@SuppressWarnings("unused") VirtualFrame frame, Object value,
      @Cached @Shared DispatchNode dispatchNode) {
    Object resultBuilder = frame.getObjectStatic(getResultSlot());
    Object result = dispatchNode.executeDispatch(templates, value, null, resultBuilder);
    frame.setObjectStatic(getResultSlot(), result);
  }

  @GenerateInline(value = false)
  public static abstract class DispatchNode extends Node {
    public abstract Object executeDispatch(Templates templates, Object currentValue, Frame definingScope, Object resultBuilder);
    @Specialization
    protected static Object dispatchDirectly(
        @SuppressWarnings("unused") Templates templates,
        Object currentValue,
        Frame definingScope,
        Object resultBuilder,
        @Cached("create(templates.getCallTarget())") DirectCallNode directCallNode) {
      return directCallNode.call(currentValue, definingScope, resultBuilder);
    }
  }
}
