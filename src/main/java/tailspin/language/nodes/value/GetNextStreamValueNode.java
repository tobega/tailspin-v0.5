package tailspin.language.nodes.value;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.StopIterationException;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.transform.EndOfStreamException;
import tailspin.language.runtime.ValueStream;

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

  @Specialization(guards = {"stream != null"})
  @TruffleBoundary
  public Object doValueStream(ValueStream stream) {
    try {
      if (!stream.hasIteratorNextElement()) {
        throw new EndOfStreamException();
      }
      return stream.getIteratorNextElement();
    } catch (StopIterationException e) {
      throw new RuntimeException(e);
    }
  }

  @Specialization(guards = {"value != null"})
  public Object doSingle(VirtualFrame frame, Object value) {
    frame.setObjectStatic(valuesSlot, null);
    return value;
  }

  private void setValuesSlot(int valuesSlot) {
    this.valuesSlot = valuesSlot;
  }
}
