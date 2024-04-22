package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.DispatchNode;
import tailspin.language.nodes.DispatchNodeGen;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Templates;

public class SendToTemplatesNode extends ValueNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private ValueNode valueNode;

  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private DispatchNode dispatchNode;

  private final Templates templates;

  public SendToTemplatesNode(ValueNode valueNode, Templates templates) {
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
