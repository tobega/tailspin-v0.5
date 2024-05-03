package tailspin.language.nodes;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.runtime.Templates;

@GenerateInline(value = false)
public abstract class DispatchNode extends Node {
  public abstract Object executeDispatch(Templates templates, Object currentValue, Frame definingScope);
  @Specialization
  protected static Object dispatchDirectly(
      @SuppressWarnings("unused") Templates templates,
      Object currentValue,
      Frame definingScope,
      @Cached("create(templates.getCallTarget())") DirectCallNode directCallNode) {
    return directCallNode.call(currentValue, definingScope);
  }
}
