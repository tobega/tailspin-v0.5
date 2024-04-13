package tailspin.language.nodes.value;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.DispatchNode;
import tailspin.language.nodes.DispatchNodeGen;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Templates;

public class SendToTemplatesValueNode extends ValueNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private ValueNode valueNode;

  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private DispatchNode dispatchNode;

  private final Templates templates;

  public SendToTemplatesValueNode(ValueNode valueNode, Templates templates) {
    this.valueNode = valueNode;
    this.dispatchNode = DispatchNodeGen.create();
    this.templates = templates;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    Object value = valueNode.executeGeneric(frame);
    return dispatchNode.executeDispatch(templates, value);
  }
}
