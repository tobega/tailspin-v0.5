package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives;
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
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Set;

@ValueType
@ExportLibrary(InteropLibrary.class)
public final class SmallSciNum implements TruffleObject {

  private static final double BITS_PER_DIGIT = 3.322;
  public static final int DEFAULT_SMALL_PRECISION = 6;

  private static final int[] EXP_TO_LOG10 = new int[2048];
  private static final long[] MANTISSA_TOP_BREAKPOINT = new long[2048];

  static  {
    int previousIndex = -1;
    for (int i = (int) Math.floor(-1023 / BITS_PER_DIGIT); i < 1 + (int) Math.ceil(1023 / BITS_PER_DIGIT); i++) {
      double value = Math.pow(10, i);
      int exponent = Math.getExponent(value);
      int index = exponent + 1023;
      long mantissaTop = getMantissaTop(value);
      for (int j = previousIndex + 1; j <= index && j < 2048; j++) {
        EXP_TO_LOG10[j] = i - 1;
        MANTISSA_TOP_BREAKPOINT[j] = 32;
      }
      if (index < 2048) MANTISSA_TOP_BREAKPOINT[index] = mantissaTop;
      previousIndex = index;
    }
  }

  static long getMantissaTop(double value) {
    return (Double.doubleToRawLongBits(value) >>> 48) & 0b1111;
  }

  public int getBinaryExponent() {
    return Math.getExponent(value);
  }

  private final double value;
  private final int precision;

  private SmallSciNum(double value, int precision) {
    this.value = value;
    this.precision = precision;
  }

  public static SmallSciNum fromDigits(String digits, int exponent, int precision) {
    double val = Double.parseDouble(digits + "e" + exponent);
    return new SmallSciNum(val, precision);
  }

  public static SmallSciNum fromLong(long value) {
    return new SmallSciNum(value, 0);
  }

  public SmallSciNum add(SmallSciNum other) {
    double sum = value + other.value;
    if (Double.isInfinite(sum) || (sum != 0.0 && (sum < 0 ? -sum : sum) < Double.MIN_NORMAL)) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      return handleLossOfPrecision(this);
    }
    int bottomDigit = getBottomDigit(other);
    if (sum == 0.0) {
      return new SmallSciNum(0.0, bottomDigit > 0 ? 1 : 1 - bottomDigit);
    }
    int resultTop = getTopDigit(sum);
    int newPrecision = resultTop - bottomDigit + 1;
    if (newPrecision <= 0) {
      return new SmallSciNum(0.0, bottomDigit > 0 ? 1 : 1 - bottomDigit);
    }
    return new SmallSciNum(sum, newPrecision);
  }

  private int getBottomDigit(SmallSciNum other) {
    int topDigit = getTopDigit(value);
    int topDigitOther = getTopDigit(other.value);
    int bottomDigit = topDigit - precision + 1;
    int bottomDigitOther = topDigitOther - other.precision + 1;
    int result;
    if (precision == 0) {
      result = bottomDigitOther;
    } else if (other.precision == 0) {
      result = bottomDigit;
    } else {
      result = bottomDigit > bottomDigitOther ? bottomDigit : bottomDigitOther;
    }
    return result;
  }

  @TruffleBoundary
  private <T> T handleLossOfPrecision(T typeValue) {
    throw new ArithmeticException();
  }

  public SmallSciNum subtract(SmallSciNum other) {
    double diff = value - other.value;
    if (Double.isInfinite(diff) || (diff != 0.0 && (diff < 0 ? -diff : diff) < Double.MIN_NORMAL)) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      return handleLossOfPrecision(this);
    }
    int bottomDigit = getBottomDigit(other);
    if (diff == 0.0) {
      return new SmallSciNum(0.0, bottomDigit > 0 ? 1 : 1 - bottomDigit);
    }
    int resultTop = getTopDigit(diff);
    int newPrecision = resultTop - bottomDigit + 1;
    if (newPrecision <= 0) {
      return new SmallSciNum(0.0, bottomDigit > 0 ? 1 : 1 - bottomDigit);
    }
    return new SmallSciNum(diff, newPrecision);
  }

  private static int getTopDigit(double number) {
    if (number == 0.0) return 0;
    int exponent = Math.getExponent(number);
    int resultTop = EXP_TO_LOG10[exponent + 1023];
    if (getMantissaTop(number) >= MANTISSA_TOP_BREAKPOINT[exponent + 1023]) {
      resultTop++;
    }
    return resultTop;
  }

  public SmallSciNum multiply(SmallSciNum other) {
    double product = value * other.value;
    if (Double.isInfinite(product) || (product != 0.0 && (product < 0 ? -product : product) < Double.MIN_NORMAL)) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      return handleLossOfPrecision(this);
    }
    int newPrecision;
    if (precision == 0) {
      newPrecision = other.precision;
    } else if (other.precision == 0) {
      newPrecision = precision;
    } else {
      newPrecision = (precision < other.precision ? precision : other.precision);
    }
    return new SmallSciNum(product, newPrecision);
  }

  public SmallSciNum divide(SmallSciNum other) {
    double quotient = value / other.value;
    if (Double.isInfinite(quotient) || (quotient != 0.0 && (quotient < 0 ? -quotient : quotient) < Double.MIN_NORMAL)) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      return handleLossOfPrecision(this);
    }
    int newPrecision;
    if (precision == 0) {
      newPrecision = other.precision;
    } else if (other.precision == 0) {
      newPrecision = precision;
    } else {
      newPrecision = (precision < other.precision ? precision : other.precision);
    }
    return new SmallSciNum(quotient, newPrecision);
  }

  public SmallSciNum negate() {
    return new SmallSciNum(-value, precision);
  }

  @TruffleBoundary
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    int exponent;
    if (value == 0.0) {
      builder.append("0");
      if (precision > 1) {
        builder.append(".");
        builder.repeat("0", precision - 1);
      }
      exponent = 0;
    } else {
      BigDecimal bigDecimal = new BigDecimal(value, new MathContext(precision, RoundingMode.HALF_UP));
      bigDecimal = SciNum.fixPrecision(bigDecimal, precision);
      builder.append(bigDecimal.unscaledValue());
      int offset = builder.charAt(0) == '-' ? 2 : 1;
      exponent = builder.length() - offset - bigDecimal.scale();
      if (builder.length() < precision) {
        builder.repeat("0", precision - builder.length());
      }
      if (builder.length() > 1) {
        builder.insert(offset, '.');
      }
    }
    builder.append('e').append(exponent);
    return builder.toString();
  }

  public int compareTo(SmallSciNum other) {
    SmallSciNum diff = subtract(other);
    return (int) Math.signum(diff.value);
  }

  public SmallSciNum mod(SmallSciNum modulus) {
    int bottomDigit = getBottomDigit(modulus);
    double result = value % modulus.value;
    if (result < 0.0) {
      result += (modulus.value < 0 ? -modulus.value : modulus.value);
    }
    int resultTop = getTopDigit(result);
    int addPrecision = resultTop - bottomDigit + 1;
    if (addPrecision <= 0) {
      return new SmallSciNum(0.0, bottomDigit > 0 ? 1 : 1 - bottomDigit);
    }
    int newPrecision;
    if (precision == 0) {
      newPrecision = modulus.precision;
    } else if (modulus.precision == 0) {
      newPrecision = precision;
    } else {
      newPrecision = (precision < modulus.precision ? precision : modulus.precision);
    }
    return new SmallSciNum(result, newPrecision);
  }

  public long truncateDivide(SmallSciNum divisor) {
    return (long) (value / divisor.value);
  }

  public SmallSciNum squareRoot() {
    double root = Math.sqrt(value);
    if (Double.isInfinite(root) || (root != 0.0 && root < Double.MIN_NORMAL)) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      return handleLossOfPrecision(this);
    }
    int newPrecision = (precision == 0) ? DEFAULT_SMALL_PRECISION : precision;
    return new SmallSciNum(root, newPrecision);
  }

  public double getValue() {
    return value;
  }

  public int getPrecision() {
    return precision;
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
    return switch (member) {
      case "raw" -> this;
      default -> throw UnknownIdentifierException.create(member);
    };
  }

  @ExportMessage
  public Object getMembers(@SuppressWarnings("unused") boolean includeInternal) {
    return TailspinArray.value(new String[]{"raw"});
  }


  @ExportMessage
  public boolean isNumber() {
    return true;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInByte() {
    return false;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInShort() {
    return false;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInInt() {
    return false;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInLong() {
    return false;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInDouble() {
    return precision <= Double.PRECISION;
  }

  @ExportMessage
  @TruffleBoundary
  public boolean fitsInFloat() {
    return precision <= Float.PRECISION;
  }

  @ExportMessage
  public boolean fitsInBigInteger() {
    return false;
  }

  @ExportMessage
  @TruffleBoundary
  public byte asByte() throws UnsupportedMessageException {
    throw UnsupportedMessageException.create();
  }

  @ExportMessage
  @TruffleBoundary
  public short asShort() throws UnsupportedMessageException {
    throw UnsupportedMessageException.create();
  }

  @ExportMessage
  @TruffleBoundary
  public int asInt() throws UnsupportedMessageException {
    throw UnsupportedMessageException.create();
  }

  @ExportMessage
  @TruffleBoundary
  public long asLong() throws UnsupportedMessageException {
    throw UnsupportedMessageException.create();
  }

  @ExportMessage
  @TruffleBoundary
  public float asFloat() throws UnsupportedMessageException {
    return (float) value;
  }

  @ExportMessage
  @TruffleBoundary
  public double asDouble() throws UnsupportedMessageException {
    return value;
  }

  @ExportMessage
  public BigInteger asBigInteger() throws UnsupportedMessageException {
    throw UnsupportedMessageException.create();
  }
}