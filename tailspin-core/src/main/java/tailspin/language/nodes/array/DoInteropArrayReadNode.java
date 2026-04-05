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
public abstract class DoInteropArrayReadNode extends Node {
  final boolean noFail;

  protected DoInteropArrayReadNode(boolean noFail) {
    this.noFail = noFail;
  }

  public abstract Object executeInteropRead(Object array, Object index, Reference indexVar);

  @Specialization(limit = "3")
  protected Object doLong(Object array, long index, Reference indexVar,
      @CachedLibrary("array") InteropLibrary interop) {
    Object value = null;
    try {
      if (interop.isArrayElementReadable(array, index)) {
        value = interop.readArrayElement(array, index);
      } else if (!noFail) {
        throw new IndexOutOfBoundsException();
      }
    } catch (UnsupportedMessageException e) {
      throw new AssertionError(e);
    } catch (InvalidArrayIndexException e) {
      if (noFail) return null;
      throw new IndexOutOfBoundsException();
    }
    if (indexVar == null) {
      return value;
    } else {
      return new IndexedArrayValue(indexVar, index, value);
    }
  }

  @Specialization(limit = "3")
  protected Object doBigNumber(Object array, BigNumber index, Reference indexVar,
      @CachedLibrary("array") InteropLibrary interop) {
    Object value = null;
    try {
      int i = index.intValueExact();
      if (interop.isArrayElementReadable(array, i)) {
        value = interop.readArrayElement(array, i);
      } else if (!noFail) {
        throw new IndexOutOfBoundsException();
      }
    } catch (UnsupportedMessageException e) {
      throw new AssertionError(e);
    } catch (InvalidArrayIndexException e) {
      if (noFail) return null;
      throw new IndexOutOfBoundsException();
    }
    if (indexVar == null) {
      return value;
    } else {
      return new IndexedArrayValue(indexVar, index, value);
    }
  }

  @Specialization
  protected Object doArray(Object array, TailspinArray selection, Reference indexVar) {
    long length = selection.getArraySize();
    Object[] elements = new Object[Math.toIntExact(length)];
    for (int i = 0; i < length; i++) {
      elements[i] = executeInteropRead(array, selection.getNative(i, false), indexVar);
    }
    return new ListStream(elements);
  }

  @Specialization(guards = "interop.hasArrayElements(selection)", limit = "3")
  protected Object doInteropArray(
      Object array,
      Object selection,
      Reference indexVar,
      @CachedLibrary("selection") InteropLibrary interop) {
    try {
      long length = interop.getArraySize(selection);
      Object[] elements = new Object[Math.toIntExact(length)];
      for (int i = 0; i < length; i++) {
        elements[i] = executeInteropRead(array, interop.readArrayElement(selection, i), indexVar);
      }
      return new ListStream(elements);
    } catch (UnsupportedMessageException | InvalidArrayIndexException e) {
      throw new AssertionError(e);
    }
  }
}
