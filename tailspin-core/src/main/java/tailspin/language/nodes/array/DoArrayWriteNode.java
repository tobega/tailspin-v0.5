package tailspin.language.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.TypeError;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.stream.ListStream;

@GenerateInline(false)
public abstract class DoArrayWriteNode extends Node {

  public abstract void executeDirect(Object target, Object index, Object value);

  @Specialization
  protected void doLong(TailspinArray array, long index, Object value) {
    array.setNative((int) index - 1, value);
  }

  @Specialization
  protected void doBigNumber(TailspinArray array, BigNumber index, Object value) {
    array.setNative(index.intValueExact() - 1, value);
  }

  @Specialization(guards = "indexes.getArraySize() == valueStream.size()")
  protected void doArray(TailspinArray array, TailspinArray indexes, ListStream valueStream) {
    Object[] values = valueStream.getArray();
    for (int i = 0; i < indexes.getArraySize(); i++) {
      executeDirect(array, indexes.getNative(i, false), values[i]);
    }
  }

  @Specialization
  @SuppressWarnings("unused")
  protected void doIllegal(Object receiver, Object lens, Object value) {
    CompilerDirectives.transferToInterpreterAndInvalidate();
    throw new TypeError(String.format("Cannot access %s by %s to set %s", receiver.getClass(), lens.getClass(), value.getClass()),
        this);
  }
}
