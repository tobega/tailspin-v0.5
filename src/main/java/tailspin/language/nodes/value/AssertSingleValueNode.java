package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.ResultIterator;

@NodeChild(value = "resultNode", type = ValueNode.class)
public abstract class AssertSingleValueNode extends ValueNode {
  @Specialization
  public Object doIterator(ResultIterator results) {
    Object value = results.getIteratorNextElement();
    if (results.hasIteratorNextElement()) {
      throw new TypeError("Got more than one value");
    }
    return value;
  }

  @Specialization
  public Object doSingle(Object result) {
    return result;
  }
}
