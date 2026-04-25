package tailspin.language.nodes.array;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.stream.ListStream;

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

  @Specialization(guards = "indexes.getArraySize() == valueStream.size()")
  protected Object doArray(TailspinArray array, TailspinArray indexes, ListStream valueStream) {
    TailspinArray result = array.getThawed();
    Object[] values = valueStream.getArray();
    for (int i = 0; i < indexes.getArraySize(); i++) {
      executeDirect(result, indexes.getNative(i, false), values[i]);
    }
    return result;
  }

  @Specialization(guards = "arrayStream.size() == valueStream.size()")
  @SuppressWarnings("unchecked")
  protected Object doMany(ListStream arrayStream, Object index, ListStream valueStream) {
    Object[] arrays = arrayStream.getArray();
    Object[] values = valueStream.getArray();
    int size = arrayStream.size();
    for (int i = 0; i < size; i++) {
      arrays[i] = executeDirect(arrays[i], index, values[i]);
    }
    return arrayStream;
  }

  @Specialization
  @SuppressWarnings("unused")
  protected Object doIllegal(Object receiver, Object lens, Object value) {
    throw new TypeError(String.format("Cannot access %s by %s to set %s", receiver.getClass(), lens.getClass(), value.getClass()),
        this);
  }
}
