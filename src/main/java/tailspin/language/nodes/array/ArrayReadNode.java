package tailspin.language.nodes.array;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import java.util.ArrayList;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.TailspinArray;

@NodeChild(value = "array", type = ValueNode.class)
@NodeChild(value = "lens", type = ValueNode.class)
public abstract class ArrayReadNode extends ValueNode {
  public abstract Object executeDirect(Object array, Object lens);

  @Specialization
  protected Object doLong(TailspinArray array, long index) {
    return array.getNative((int) index - 1);
  }

  @Specialization
  protected Object doBigNumber(TailspinArray array, BigNumber index) {
    return array.getNative(index.intValueExact() - 1);
  }

  @Specialization
  protected Object doArray(TailspinArray array, TailspinArray selection) {
    long length = selection.getArraySize();
    ArrayList<Object> elements = new ArrayList<>();
    for (int i = 0; i < length; i++) {
      elements.add(executeDirect(array, selection.getNative(i)));
    }
    return elements;
  }

  @Specialization
  protected Object doRange(TailspinArray array, ArrayList<?> range) {
    ArrayList<Object> elements = new ArrayList<>();
    for (Object o : range) {
      elements.add(executeDirect(array, o));
    }
    return elements;
  }

  @Specialization
  @SuppressWarnings("unchecked")
  protected Object doMultiSelect(ArrayList<?> multiple, Object selection) {
    ((ArrayList<Object>) multiple).replaceAll(array -> executeDirect(array, selection));
    return multiple;
  }

  @Specialization
  protected Object doIllegal(Object receiver, Object lens) {
    throw new TypeError(String.format("Cannot read %s by %s", receiver.getClass(), lens.getClass()));
  }

  public static ArrayReadNode create(ValueNode array, ValueNode lens) {
    return ArrayReadNodeGen.create(array, lens);
  }
}
