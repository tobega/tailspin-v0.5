package tailspin.language.nodes.array;

import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.IndexedArrayValue;
import tailspin.language.runtime.Reference;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.stream.ListStream;

@GenerateInline(value = false)
public abstract class DoArrayReadNode extends Node {
  final boolean noFail;

  protected DoArrayReadNode(boolean noFail) {
    this.noFail = noFail;
  }

  public abstract Object executeArrayRead(TailspinArray array, Object index, Reference indexVar);

  @Specialization
  protected Object doLong(TailspinArray array, long index, Reference indexVar) {
    Object value = array.getNative((int) index - 1, noFail);
    if (indexVar == null) {
      return value;
    } else {
      return new IndexedArrayValue(indexVar, index, value);
    }
  }

  @Specialization
  protected Object doBigNumber(TailspinArray array, BigNumber index, Reference indexVar) {
    Object value = array.getNative(index.intValueExact() - 1, noFail);
    if (indexVar == null) {
      return value;
    } else {
      return new IndexedArrayValue(indexVar, index, value);
    }
  }

  @Specialization
  protected Object doArray(TailspinArray array, TailspinArray selection, Reference indexVar) {
    long length = selection.getArraySize();
    Object[] elements = new Object[Math.toIntExact(length)];
    for (int i = 0; i < length; i++) {
      elements[i] = executeArrayRead(array, selection.getNative(i, false), indexVar);
    }
    return new ListStream(elements);
  }

  @Specialization(guards = "interop.hasArrayElements(selection)", limit = "3")
  protected Object doInteropArray(
      TailspinArray array,
      Object selection,
      Reference indexVar,
      @CachedLibrary("selection") InteropLibrary interop) {
    try {
      long length = interop.getArraySize(selection);
      Object[] elements = new Object[Math.toIntExact(length)];
      for (int i = 0; i < length; i++) {
        elements[i] = executeArrayRead(array, interop.readArrayElement(selection, i), indexVar);
      }
      return new ListStream(elements);
    } catch (UnsupportedMessageException | InvalidArrayIndexException e) {
      throw new AssertionError(e);
    }
  }
}
