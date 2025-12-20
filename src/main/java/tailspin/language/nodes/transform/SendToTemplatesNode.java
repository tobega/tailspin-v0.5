package tailspin.language.nodes.transform;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Idempotent;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.state.GetDefiningScopeNode;
import tailspin.language.runtime.DefiningScope;
import tailspin.language.runtime.Templates;

@NodeChild(value = "value", type = ValueNode.class)
public abstract class SendToTemplatesNode extends TransformNode {
  private final Templates templates;
  protected final int callLevel;

  protected SendToTemplatesNode(Templates templates, int callLevel, SourceSection sourceSection) {
    super(sourceSection);
    this.callLevel = callLevel;
    this.templates = templates;
  }

  public static SendToTemplatesNode create(ValueNode valueNode, int callLevel, Templates templates,
      SourceSection sourceSection) {
    return SendToTemplatesNodeGen.create(templates, callLevel, sourceSection, valueNode);
  }

  @Specialization(guards = "contextFrameLevel() >= 0")
  public void doDispatch(VirtualFrame frame, Object value,
      @Cached(inline = true)GetDefiningScopeNode getScope,
      @Cached @Shared DispatchNode dispatchNode) {
    DefiningScope scope = getScope.execute(frame, this, contextFrameLevel());
    Object resultBuilder = frame.getObjectStatic(getResultSlot());
    Object result = dispatchNode.executeDispatch(templates, value, scope,
        resultBuilder);
    frame.setObjectStatic(getResultSlot(), result);
  }

  @Idempotent
  int contextFrameLevel() {
    return templates.needsScope() ? callLevel - templates.getDefinitionLevel() : -1;
  }

  @Specialization(guards = "contextFrameLevel() < 0")
  public void doFree(@SuppressWarnings("unused") VirtualFrame frame, Object value,
      @Cached @Shared DispatchNode dispatchNode) {
    Object resultBuilder = frame.getObjectStatic(getResultSlot());
    Object result = dispatchNode.executeDispatch(templates, value, null, resultBuilder);
    frame.setObjectStatic(getResultSlot(), result);
  }

  @GenerateInline(value = false)
  public static abstract class DispatchNode extends Node {
    public abstract Object executeDispatch(Templates templates, Object currentValue, DefiningScope definingScope, Object resultBuilder);

    @Specialization
    protected static Object dispatchDirectly(
        @SuppressWarnings("unused") Templates templates,
        Object currentValue,
        DefiningScope definingScope,
        Object resultBuilder,
        @Cached("create(templates.getCallTarget())") DirectCallNode directCallNode) {
      return directCallNode.call(definingScope, currentValue, resultBuilder);
    }
  }
}
