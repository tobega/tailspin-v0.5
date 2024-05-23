package tailspin.language.nodes.array;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import java.math.BigInteger;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinArray;

@NodeChild(value = "array", type = ValueNode.class)
@NodeChild(value = "lens", type = ValueNode.class)
public abstract class ArrayReadNode extends ValueNode {
  @Specialization
  protected Object doLong(TailspinArray array, long index) {
    return array.getNative((int) index - 1);
  }

  @Specialization
  protected Object doBigInteger(TailspinArray array, BigInteger index) {
    return array.getNative(index.intValueExact() - 1);
  }

  @Specialization
  protected Object doIllegal(Object receiver, Object lens) {
    throw new TypeError(String.format("Cannot read %s by %s", receiver.getClass(), lens.getClass()));
  }

  public static ArrayReadNode create(ValueNode array, ValueNode lens) {
    return ArrayReadNodeGen.create(array, lens);
  }
}
