package tailspin.language.nodes.state;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinArray;

@NodeChild(value = "result", type = ValueNode.class)
public abstract class FreezeNode extends ValueNode {
  @Specialization
  long doLong(long number) {
    return number;
  }

  @Specialization
  TailspinArray doArray(TailspinArray ta) {
    ta.freeze();
    return ta;
  }

  @Specialization
  Object doObject(Object result) {
    return result;
  }

  public static FreezeNode create(ValueNode result) {
    return FreezeNodeGen.create(result);
  }
}
