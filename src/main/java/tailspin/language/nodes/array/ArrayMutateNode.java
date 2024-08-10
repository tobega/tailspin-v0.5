package tailspin.language.nodes.array;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.TailspinArray;

@NodeChild(value = "array", type = ValueNode.class)
@NodeChild(value = "lens", type = ValueNode.class)
@NodeChild(value = "value", type = ValueNode.class)
public abstract class ArrayMutateNode extends ValueNode {

  public static ArrayMutateNode create(ValueNode array, ValueNode index, ValueNode value) {
    return ArrayMutateNodeGen.create(array, index, value);
  }

  @Specialization
  protected Object doLong(TailspinArray array, long index, Object value) {
    TailspinArray result = array.getThawed();
    result.setNative((int) index - 1, value);
    return result;
  }

  @Specialization
  protected Object doBigNumber(TailspinArray array, BigNumber index, Object value) {
    TailspinArray result = array.getThawed();
    result.setNative(index.intValueExact() - 1, value);
    return result;
  }

  @Specialization
  @SuppressWarnings("unused")
  protected Object doIllegal(Object receiver, Object lens, Object value) {
    throw new TypeError(String.format("Cannot read %s by %s", receiver.getClass(), lens.getClass()));
  }
}
