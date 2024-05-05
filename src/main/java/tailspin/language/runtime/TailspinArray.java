package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.util.Arrays;

@ExportLibrary(InteropLibrary.class)
public class TailspinArray implements TruffleObject {
  private final Object[] arrayElements;
  private final long length;
  private boolean isMutable;

  private TailspinArray(Object[] arrayElements, long length, boolean isMutable) {
    this.arrayElements = arrayElements;
    this.length = length;
    this.isMutable = isMutable;
  }

  public static TailspinArray value(Object[] arrayElements) {
    return new TailspinArray(arrayElements, arrayElements.length, false);
  }
  public TailspinArray getThawed() {
    if (isMutable) return this;
    return new TailspinArray(Arrays.copyOf(arrayElements, arrayElements.length), length, true);
  }

  @TruffleBoundary
  public void freeze() {
    if (!isMutable) return;
    isMutable = false;
    for (int i = 0; i < length; i++) {
      if (arrayElements[i] instanceof TailspinArray ta) {
        ta.freeze();
      }
    }
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
  public boolean hasArrayElements() {
    return true;
  }

  @ExportMessage
  public long getArraySize() {
    return length;
  }

  @ExportMessage
  public boolean isArrayElementReadable(long index) {
    return index >= 0 && index < length;
  }

  @ExportMessage
  public Object readArrayElement(long index) throws InvalidArrayIndexException {
    if (!isArrayElementReadable(index)) throw InvalidArrayIndexException.create(index);
    return this.arrayElements[(int) index];
  }

  @ExportMessage
  public boolean isArrayElementModifiable(@SuppressWarnings("unused") long index) {
    return false;
  }

  @ExportMessage
  public boolean isArrayElementInsertable(@SuppressWarnings("unused") long index) {
    return false;
  }

  @ExportMessage
  @SuppressWarnings("unused")
  public void writeArrayElement(long index, Object value) throws UnsupportedMessageException {
    throw UnsupportedMessageException.create();
  }

  @ExportMessage
  public boolean hasMembers() {
    return true;
  }

  @ExportMessage
  public boolean isMemberReadable(String member) {
    return "length".equals(member);
  }

  @ExportMessage
  public Object readMember(String member) throws UnknownIdentifierException {
    return switch(member) {
      case "length" -> length;
      default -> throw UnknownIdentifierException.create(member);
    };
  }

  @ExportMessage
  public Object getMembers(@SuppressWarnings("unused") boolean includeInternal) {
    return TailspinArray.value(new String[]{"length"});
  }
}
