package tailspin.language.nodes.value;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.transform.EndOfStreamException;
import tailspin.language.runtime.ResultIterator;

public class GetNextStreamValueNode extends ValueNode {
  private final int valuesSlot;

  public GetNextStreamValueNode(int valuesSlot) {
    this.valuesSlot = valuesSlot;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    Object value = frame.getObjectStatic(valuesSlot);
    if (value == null) throw new EndOfStreamException();
    if (value instanceof ResultIterator values) {
      if (!values.hasIteratorNextElement())
        throw new EndOfStreamException();
      return values.getIteratorNextElement();
    }
    frame.setObjectStatic(valuesSlot, null);
    return value;
  }
}
