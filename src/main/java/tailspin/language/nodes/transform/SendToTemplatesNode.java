package tailspin.language.nodes.transform;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.runtime.TemplatesInstance;

public abstract class SendToTemplatesNode extends TransformNode {

  @Child @Executed
  @SuppressWarnings("FieldMayBeFinal")
  protected ValueNode valueNode;

  @Child @Executed
  @SuppressWarnings("FieldMayBeFinal")
  protected ValueNode templatesNode;

  protected SendToTemplatesNode(int chainCvSlot, int level, int templatesSlot) {
    this.valueNode = ReadContextValueNode.create(-1, chainCvSlot);
    this.templatesNode = ReadContextValueNode.create(level, templatesSlot);
  }

  public static SendToTemplatesNode create(int chainCvSlot, int level, int templatesSlot) {
    return SendToTemplatesNodeGen.create(chainCvSlot, level, templatesSlot);
  }

  @Specialization
  public void doDispatch(VirtualFrame frame, Object value, TemplatesInstance templates,
      @Cached DispatchNode dispatchNode) {
    Object resultBuilder = frame.getObjectStatic(getResultSlot());
    Object result = dispatchNode.executeDispatch(templates, value, resultBuilder);
    frame.setObjectStatic(getResultSlot(), result);
  }

  @GenerateInline(value = false)
  public static abstract class DispatchNode extends Node {
    public abstract Object executeDispatch(TemplatesInstance templates, Object currentValue, Object resultBuilder);
    @Specialization
    protected static Object dispatchDirectly(
        @SuppressWarnings("unused") TemplatesInstance templates,
        Object currentValue,
        Object resultBuilder,
        @Cached("create(templates.callTarget())") DirectCallNode directCallNode) {
      return directCallNode.call(templates.definingScope(), currentValue, resultBuilder);
    }
  }
}
