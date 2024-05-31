package tailspin.language.nodes.transform;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import java.util.ArrayList;
import tailspin.language.TypeError;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.TransformResultNode;

@SuppressWarnings("unused")
@NodeChild(value = "result", type = ValueNode.class)
public abstract class SinkNode extends StatementNode {
  @Specialization(guards = "result == null")
  void doNull(Object result) {
    // all good
  }

  @Specialization(guards = {"stream != null"})
  @TruffleBoundary
  void doEmpty(ArrayList<?> stream) {
    if (!stream.isEmpty())
      throw new TypeError("Got unexpected values from sink " + stream);
  }

  @Specialization
  void unexpectedValues(Object result) {
    throw new TypeError("Got unexpected value from sink " + result);
  }

  public static SinkNode create(TransformNode result) {
    return SinkNodeGen.create(new TransformResultNode(result));
  }
}
