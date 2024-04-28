package tailspin.language.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.util.Arrays;

@ExportLibrary(InteropLibrary.class)
public class TailspinArray implements TruffleObject {
  private final Object[] arrayElements;
  private final long length;
  private final boolean isMutable;

  private TailspinArray(Object[] arrayElements, long length, boolean isMutable) {
    this.arrayElements = arrayElements;
    this.length = length;
    this.isMutable = isMutable;
  }

  public static TailspinArray value(Object[] arrayElements, long length) {
    return new TailspinArray(arrayElements, length, false);
  }
  public TailspinArray getThawed() {
    if (isMutable) return this;
    return new TailspinArray(Arrays.copyOf(arrayElements, arrayElements.length), length, true);
  }

  public Object getNative(int i) {
    if (!isArrayElementReadable(i)) throw new IndexOutOfBoundsException();
    return arrayElements[i];
  }

  public void setNative(int i, Object value) {
    if (!isMutable) throw new IllegalStateException();
    if (!isArrayElementReadable(i)) throw new IndexOutOfBoundsException();
    arrayElements[i] = value;
  }

  @ExportMessage
  boolean hasArrayElements() {
    return true;
  }

  @ExportMessage
  long getArraySize() {
    return length;
  }

  @ExportMessage
  boolean isArrayElementReadable(long index) {
    return index >= 0 && index < length;
  }

  @ExportMessage
  Object readArrayElement(long index) throws InvalidArrayIndexException {
    if (!isArrayElementReadable(index)) throw InvalidArrayIndexException.create(index);
    return this.arrayElements[(int) index];
  }

  @ExportMessage
  boolean isArrayElementModifiable(@SuppressWarnings("unused") long index) {
    return false;
  }

  @ExportMessage
  boolean isArrayElementInsertable(@SuppressWarnings("unused") long index) {
    return false;
  }

  @ExportMessage
  @SuppressWarnings("unused")
  void writeArrayElement(long index, Object value) throws UnsupportedMessageException {
    throw UnsupportedMessageException.create();
  }
}
