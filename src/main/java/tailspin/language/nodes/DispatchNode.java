package tailspin.language.nodes;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import java.util.Iterator;
import tailspin.language.runtime.Templates;

@GenerateInline(value = false)
public abstract class DispatchNode extends Node {
  public abstract Iterator<Object> executeDispatch(Templates templates, Object currentValue);
  @Specialization
  protected static Iterator<Object> dispatchDirectly(
      @SuppressWarnings("unused") Templates templates,
      Object currentValue,
      @Cached("create(templates.getCallTarget())") DirectCallNode directCallNode) {
    @SuppressWarnings("unchecked")
    Iterator<Object> result = (Iterator<Object>) directCallNode.call(new Object[]{currentValue});
    return result;
  }
}
