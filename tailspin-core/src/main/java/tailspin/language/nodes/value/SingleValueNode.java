package tailspin.language.nodes.value;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import java.util.ArrayList;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;

public abstract class SingleValueNode extends ValueNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child @Executed
  TransformResultNode transformResult;

  SingleValueNode(TransformNode transformNode, SourceSection sourceSection) {
    super(sourceSection);
    this.transformResult = new TransformResultNode(transformNode, sourceSection);
  }

  public static SingleValueNode create(TransformNode transformNode) {
    return SingleValueNodeGen.create(transformNode, INTERNAL_CODE_SOURCE);
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
