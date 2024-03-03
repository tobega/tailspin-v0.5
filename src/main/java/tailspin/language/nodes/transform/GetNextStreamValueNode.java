package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.Iterator;
import tailspin.language.nodes.ExpressionNode;

public class GetNextStreamValueNode extends ExpressionNode {
  private final int valuesSlot;

  public GetNextStreamValueNode(int valuesSlot) {
    this.valuesSlot = valuesSlot;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    @SuppressWarnings("unchecked")
    Iterator<Object> values = (Iterator<Object>) frame.getObject(valuesSlot);
    if (!values.hasNext()) throw new EndOfStreamException();
    return values.next();
  }
}
