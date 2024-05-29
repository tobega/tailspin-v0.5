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

public abstract class BuildFromTemplatesNode extends ValueNode {

  @Child @Executed
  @SuppressWarnings("FieldMayBeFinal")
  protected ValueNode valueNode;

  private final Templates templates;
  protected final int definitionLevel;
  protected final int resultSlot;

  protected BuildFromTemplatesNode(int chainCvSlot, Templates templates, int definitionLevel,
      int resultSlot) {
    this.valueNode = LocalReferenceNode.create(chainCvSlot);
    this.definitionLevel = definitionLevel;
    this.templates = templates;
    this.resultSlot = resultSlot;
  }

  public static BuildFromTemplatesNode create(int chainCvSlot, Templates templates, int definitionLevel, int resultSlot) {
    return BuildFromTemplatesNodeGen.create(chainCvSlot, templates, definitionLevel, resultSlot);
  }

  @Specialization(guards = "definitionLevel >= 0")
  public Object doDispatch(VirtualFrame frame, Object value,
      @Cached(inline = true) GetContextFrameNode getContextFrameNode,
      @Cached @Shared DispatchNode dispatchNode) {
    VirtualFrame contextFrame = getContextFrameNode.execute(frame, this, definitionLevel);
    Object resultBuilder = frame.getObjectStatic(resultSlot);
    Object result = dispatchNode.executeDispatch(templates, value, contextFrame.materialize(),
        resultBuilder);
    frame.setObjectStatic(resultSlot, result);
    return null;
  }

  @Specialization(guards = "definitionLevel < 0")
  public Object doFree(@SuppressWarnings("unused") VirtualFrame frame, Object value,
      @Cached @Shared DispatchNode dispatchNode) {
    Object resultBuilder = frame.getObjectStatic(resultSlot);
    return dispatchNode.executeDispatch(templates, value, null, resultBuilder);
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
