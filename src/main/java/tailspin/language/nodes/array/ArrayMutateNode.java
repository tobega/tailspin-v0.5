package tailspin.language.nodes.array;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import java.util.ArrayList;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.TailspinArray;

@NodeChild(value = "array", type = ValueNode.class)
@NodeChild(value = "lens", type = ValueNode.class)
@NodeChild(value = "value", type = ValueNode.class)
public abstract class ArrayMutateNode extends ValueNode {

  public ArrayMutateNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  public abstract Object executeDirect(Object target, Object index, Object value);

  public static ArrayMutateNode create(ValueNode array, ValueNode index, ValueNode value,
      SourceSection sourceSection) {
    return ArrayMutateNodeGen.create(sourceSection, array, index, value);
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

  @Specialization(guards = "indexes.getArraySize() == values.size()")
  protected Object doArray(TailspinArray array, TailspinArray indexes, ArrayList<?> values) {
    TailspinArray result = array.getThawed();
    for (int i = 0; i < indexes.getArraySize(); i++) {
      executeDirect(result, indexes.getNative(i, false), values.get(i));
    }
    return result;
  }

  @Specialization(guards = "arrays.size() == values.size()")
  @SuppressWarnings("unchecked")
  protected Object doMany(ArrayList<?> arrays, Object index, ArrayList<?> values) {
    for (int i = 0; i < arrays.size(); i++) {
      ((ArrayList<Object>) arrays).set(i, executeDirect(arrays.get(i), index, values.get(i)));
    }
    return arrays;
  }

  @Specialization
  @SuppressWarnings("unused")
  protected Object doIllegal(Object receiver, Object lens, Object value) {
    throw new TypeError(String.format("Cannot access %s by %s", receiver.getClass(), lens.getClass()),
        this);
  }
}
