package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import java.util.ArrayList;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;

public abstract class SingleValueNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  TransformResultNode transformResult;

  SingleValueNode(TransformNode transformNode) {
    this.transformResult = new TransformResultNode(transformNode);
  }

  public static SingleValueNode create(TransformNode transformNode) {
    return SingleValueNodeGen.create(transformNode);
  }

  @Specialization
  long doLong(long value) {
    return value;
  }

  @Specialization
  BigNumber doBigNumber(BigNumber value) {
    return value;
  }

  @Specialization
  Object doResultIterator(ArrayList<?> values) {
    if (values.size() != 1) throw new AssertionError("Not single value " + values);
    return values.getFirst();
  }

  @Specialization(guards = "value == null")
  Object doNull(@SuppressWarnings("unused") Object value) {
    throw new AssertionError("No value");
  }

  @Fallback
  Object doObject(Object value) {
    return value;
  }

}
