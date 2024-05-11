package tailspin.language.nodes.transform;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import tailspin.language.TypeError;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;

@SuppressWarnings("unused")
@NodeChild(value = "result", type = ValueNode.class)
public abstract class SinkNode extends StatementNode {
  @Specialization(guards = "result == null")
  void doNull(Object result) {
    // all good
  }

  @Specialization(limit = "2", guards = {"result != null", "resultInteropLibrary.isIterator(result)"})
  void doEmpty(Object result,
      @CachedLibrary("result") InteropLibrary resultInteropLibrary) {
    try {
      if (resultInteropLibrary.hasIteratorNextElement(result))
        throw new TypeError("Got unexpected value from sink " + resultInteropLibrary.getIteratorNextElement(result));
    } catch (UnsupportedMessageException | StopIterationException e) {
      throw new RuntimeException(e);
    }
  }

  @Fallback
  void unexpectedValues(Object result) {
    throw new TypeError("Got unexpected value from sink " + result);
  }

  public static SinkNode create(ValueNode result) {
    return SinkNodeGen.create(result);
  }
}
