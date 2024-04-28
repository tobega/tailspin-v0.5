package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.transform.EndOfStreamException;

@SuppressWarnings("unused")
@NodeChild(value = "valuesNode", type = StaticReferenceNode.class)
public abstract class GetNextStreamValueNode extends ValueNode {

  private int valuesSlot;

  public static GetNextStreamValueNode create(int valuesSlot) {
    GetNextStreamValueNode result = GetNextStreamValueNodeGen.create(
        StaticReferenceNode.create(valuesSlot));
    result.setValuesSlot(valuesSlot);
    return result;
  }

  @Specialization
  public long doLong(VirtualFrame frame, long value) {
    frame.setObjectStatic(valuesSlot, null);
    return value;
  }

  @Specialization(guards = "value == null")
  public Object doNull(VirtualFrame frame, Object value) {
    throw new EndOfStreamException();
  }

  @Specialization(limit = "2", guards = {"value != null", "!valueInteropLibrary.isIterator(value)"})
  public Object doSingle(VirtualFrame frame, Object value,
      @CachedLibrary("value") InteropLibrary valueInteropLibrary) {
    frame.setObjectStatic(valuesSlot, null);
    return value;
  }

  @Specialization(limit = "2", guards = {"value != null", "valueInteropLibrary.isIterator(value)"})
  public Object doIterator(VirtualFrame frame, Object value,
      @CachedLibrary("value") InteropLibrary valueInteropLibrary) {
    try {
      if (!valueInteropLibrary.hasIteratorNextElement(value)) {
        throw new EndOfStreamException();
      }
      return valueInteropLibrary.getIteratorNextElement(value);
    } catch (UnsupportedMessageException | StopIterationException e) {
      throw new RuntimeException(e);
    }
  }

  private void setValuesSlot(int valuesSlot) {
    this.valuesSlot = valuesSlot;
  }
}
