package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

@ExportLibrary(InteropLibrary.class)
@ValueType
public class BigNumber implements TruffleObject, Comparable<BigNumber> {
  private final BigInteger value;

  public BigNumber(BigInteger value) {
    this.value = value;
  }

  @ExportMessage
  public boolean isNumber() {
    return true;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInByte() {
    return value.bitLength() < Byte.SIZE;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInShort() {
    return value.bitLength() < Short.SIZE;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInInt() {
    return value.bitLength() < Integer.SIZE;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInLong() {
    return value.bitLength() < Long.SIZE;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInDouble() {
    if (value.bitLength() <= 53) { // 53 = size of double mantissa + 1
      return true;
    } else {
      double doubleValue = value.doubleValue();
      if (!Double.isFinite(doubleValue)) {
        return false;
      }
      return new BigDecimal(doubleValue).toBigIntegerExact().equals(value);
    }
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInFloat() {
    if (value.bitLength() <= 24) { // 24 = size of float mantissa + 1
      return true;
    } else {
      float floatValue = value.floatValue();
      if (!Float.isFinite(floatValue)) {
        return false;
      }
      return new BigDecimal(floatValue).toBigIntegerExact().equals(value);
    }
  }

  @ExportMessage
  public boolean fitsInBigInteger() {
    return true;
  }

  @ExportMessage
  @TruffleBoundary
  public byte asByte() throws UnsupportedMessageException {
    try {
      return value.byteValueExact();
    } catch (ArithmeticException e) {
      throw UnsupportedMessageException.create();
    }
  }

  @ExportMessage
  @TruffleBoundary
  public short asShort() throws UnsupportedMessageException {
    try {
      return value.shortValueExact();
    } catch (ArithmeticException e) {
      throw UnsupportedMessageException.create();
    }
  }

  @ExportMessage
  @TruffleBoundary
  public int asInt() throws UnsupportedMessageException {
    try {
      return value.intValueExact();
    } catch (ArithmeticException e) {
      throw UnsupportedMessageException.create();
    }
  }

  @ExportMessage
  @TruffleBoundary
  public long asLong() throws UnsupportedMessageException {
    try {
      return value.longValueExact();
    } catch (ArithmeticException e) {
      throw UnsupportedMessageException.create();
    }
  }

  @ExportMessage
  @TruffleBoundary
  public float asFloat() throws UnsupportedMessageException {
    if (fitsInFloat()) {
      return value.floatValue();
    } else {
      throw UnsupportedMessageException.create();
    }
  }

  @ExportMessage
  @TruffleBoundary
  public double asDouble() throws UnsupportedMessageException {
    if (fitsInDouble()) {
      return value.doubleValue();
    } else {
      throw UnsupportedMessageException.create();
    }
  }

  @ExportMessage
  public BigInteger asBigInteger() {
    return value;
  }

  @TruffleBoundary
  public BigNumber add(BigNumber right) {
    return new BigNumber(value.add(right.value));
  }

  @TruffleBoundary
  public BigNumber subtract(BigNumber right) {
    return new BigNumber(value.subtract(right.value));
  }

  @TruffleBoundary
  public BigNumber multiply(BigNumber right) {
    return new BigNumber(value.multiply(right.value));
  }

  @TruffleBoundary
  public BigNumber divide(BigNumber right) {
    return new BigNumber(value.divide(right.value));
  }

  @TruffleBoundary
  public BigNumber mod(BigNumber right) {
    return new BigNumber(value.mod(right.value.abs()));
  }

  public BigNumber negate() {
    return new BigNumber(value.negate());
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof BigNumber b) return value.equals(b.value);
    else return value.equals(obj);
  }

  @Override
  public String toString() {
    return value.toString();
  }

  @TruffleBoundary
  public int intValueExact() {
    return value.intValueExact();
  }

  @Override
  public int compareTo(BigNumber right) {
    return value.compareTo(right.value);
  }

  @ExportMessage
  public boolean hasMembers() {
    return true;
  }

  static final Set<String> supportedMessages = Set.of("raw");

  @ExportMessage
  public boolean isMemberReadable(String member) {
    return supportedMessages.contains(member);
  }

  @ExportMessage
  public Object readMember(String member) throws UnknownIdentifierException {
    return switch(member) {
      case "raw" -> this;
      default -> throw UnknownIdentifierException.create(member);
    };
  }

  @ExportMessage
  public Object getMembers(@SuppressWarnings("unused") boolean includeInternal) {
    return TailspinArray.value(new String[]{"raw"});
  }
}
