package tailspin.language.nodes.transform;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TemplatesInstance;

@NodeChild(value = "value", type = ValueNode.class)
@NodeChild(value = "templates", type = ValueNode.class)
public abstract class CallDefinedTemplatesNode extends TransformNode {

  public CallDefinedTemplatesNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  public static CallDefinedTemplatesNode create(ValueNode value, ValueNode templates) {
    return CallDefinedTemplatesNodeGen.create(INTERNAL_CODE_SOURCE, value, templates);
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
