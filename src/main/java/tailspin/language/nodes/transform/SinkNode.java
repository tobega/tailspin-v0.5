package tailspin.language.nodes.transform;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.StopIterationException;
import tailspin.language.TypeError;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.ResultIterator;

@SuppressWarnings("unused")
@NodeChild(value = "result", type = ValueNode.class)
public abstract class SinkNode extends StatementNode {
  @Specialization(guards = "result == null")
  void doNull(Object result) {
    // all good
  }

  @Specialization(guards = {"stream != null"})
  @TruffleBoundary
  void doEmpty(ResultIterator stream) {
    try {
      if (stream.hasIteratorNextElement())
        throw new TypeError("Got unexpected value from sink " + stream.getIteratorNextElement());
    } catch (StopIterationException e) {
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
