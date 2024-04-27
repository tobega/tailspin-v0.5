package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.ResultIterator;

@SuppressWarnings("unused")
@NodeChild(value = "resultNode", type = ValueNode.class)
public abstract class AssertSingleValueNode extends ValueNode {
  @Specialization
  public long doLong(long value) {
    return value;
  }

  @Specialization(guards = "value == null")
  public Object doNull(Object value) {
    throw new TypeError("Got no value");
  }

  @Specialization(limit = "2", guards = {"value != null", "!valueInteropLibrary.isIterator(value)"})
  public Object doSingle(Object value,
      @CachedLibrary("value") InteropLibrary valueInteropLibrary) {
    return value;
  }

  @Specialization(limit = "2", guards = {"results != null", "valueInteropLibrary.isIterator(results)"})
  public Object doIterator(Object results,
      @CachedLibrary("results") InteropLibrary valueInteropLibrary) {
    try {
      Object value = valueInteropLibrary.getIteratorNextElement(results);
      if (valueInteropLibrary.hasIteratorNextElement(results)) {
        throw new TypeError("Got more than one value");
      }
      return value;
    } catch (UnsupportedMessageException | StopIterationException e) {
      throw new TypeError("Got no value");
    }
  }

  protected static boolean isIterator(Object result) {
    return (result instanceof ResultIterator);
  }
}
