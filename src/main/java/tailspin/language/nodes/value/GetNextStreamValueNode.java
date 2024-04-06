package tailspin.language.nodes.value;

import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.Iterator;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.transform.EndOfStreamException;

public class GetNextStreamValueNode extends ValueNode {
  private final int valuesSlot;

  public GetNextStreamValueNode(int valuesSlot) {
    this.valuesSlot = valuesSlot;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    @SuppressWarnings("unchecked")
    Iterator<Object> values = (Iterator<Object>) frame.getObjectStatic(valuesSlot);
    if (!values.hasNext()) throw new EndOfStreamException();
    return values.next();
  }
}
