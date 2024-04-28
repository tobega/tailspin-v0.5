package tailspin.language.nodes.array;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import java.math.BigInteger;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinArray;

@NodeChild(value = "array", type = ValueNode.class)
@NodeChild(value = "lens", type = ValueNode.class)
@NodeChild(value = "value", type = ValueNode.class)
public abstract class ArrayWriteNode extends ValueNode {

  public static ArrayWriteNode create(ValueNode array, ValueNode index, ValueNode value) {
    return ArrayWriteNodeGen.create(array, index, value);
  }

  @Specialization
  protected Object doLong(TailspinArray array, long index, Object value) {
    TailspinArray result = array.getThawed();
    result.setNative((int) index - 1, value);
    return result;
  }

  @Specialization
  protected Object doBigInteger(TailspinArray array, BigInteger index, Object value) {
    TailspinArray result = array.getThawed();
    result.setNative(index.intValueExact() - 1, value);
    return result;
  }

  @Fallback
  @SuppressWarnings("unused")
  protected Object doIllegal(Object receiver, Object lens, Object value) {
    throw new TypeError(String.format("Cannot read %s by %s", receiver.getClass(), lens.getClass()));
  }
}
