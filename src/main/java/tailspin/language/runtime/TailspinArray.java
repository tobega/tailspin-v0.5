package tailspin.language.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

@ExportLibrary(InteropLibrary.class)
public class TailspinArray implements TruffleObject {
  private Object[] arrayElements;
  private long length;
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

  public boolean needsFreeze() {
    if (isMutable) {
      isMutable = false;
      return true;
    }
    return false;
  }

  public Object getNative(int i, boolean noFail) {
    if (!isArrayElementReadable(i)) {
      if (noFail) return null;
      throw new IndexOutOfBoundsException();
    }
    return arrayElements[i];
  }

  public void setNative(int i, Object value) {
    if (!isMutable) throw new IllegalStateException();
    if (!isArrayElementReadable(i)) throw new IndexOutOfBoundsException();
    arrayElements[i] = value;
  }

  public Object stream() {
    if (length == 0) return null;
    if (length == 1) return arrayElements[0];
    return new ArrayList<>(Arrays.asList(arrayElements).subList(0, (int) length));
  }

  public void append(Object value) {
    if (!isMutable) throw new IllegalStateException();
    if (length == arrayElements.length) {
      grow((int) (length + 1));
    }
    arrayElements[(int) length] = value;
    length += 1;
  }

  /**
   * Increases the capacity to ensure that it can hold at least the
   * number of elements specified by the minimum capacity argument.
   *
   * @param minCapacity the desired minimum capacity
   * @throws OutOfMemoryError if minCapacity is less than zero
   */
  private void grow(int minCapacity) {
    int oldCapacity = arrayElements.length;
    int newCapacity = Math.max(minCapacity, oldCapacity + (oldCapacity >> 1));
    arrayElements = Arrays.copyOf(arrayElements, newCapacity);
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

  final Set<String> supportedMessages = Set.of("first", "last", "length");

  @ExportMessage
  public boolean isMemberReadable(String member) {
    return supportedMessages.contains(member);
  }

  @ExportMessage
  public Object readMember(String member) throws UnknownIdentifierException {
    return switch(member) {
      case "first" -> 1L;
      case "last" -> length;
      case "length" -> length;
      default -> throw UnknownIdentifierException.create(member);
    };
  }

  @ExportMessage
  public Object getMembers(@SuppressWarnings("unused") boolean includeInternal) {
    return TailspinArray.value(new String[]{"length"});
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < length; i++) {
      result.append(", ");
      if (arrayElements[i] instanceof TruffleString ts) {
        result.append(TailspinStrings.escapeAndQuote(ts));
      } else {
        result.append(arrayElements[i]);
      }
    }
    result.replace(0, 2, "[").append("]");
    return result.toString();
  }
}
