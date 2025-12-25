package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Set;

@ValueType
@ExportLibrary(InteropLibrary.class)
public class SciNum implements TruffleObject {
  private final BigDecimal value;
  private final boolean isExact;

  private SciNum(BigDecimal value, int precision) {
    if (precision == 0) {
      isExact = true;
      this.value = value;
    } else {
      isExact = false;
      this.value = fixPrecision(value, precision);
    }
  }

  private static BigDecimal fixPrecision(BigDecimal result, int precision) {
    if (result.compareTo(BigDecimal.ZERO) == 0) {
      result = result.setScale(precision - 1, RoundingMode.HALF_UP);
    } else if (precisionOf(result) < precision) {
      int expand = precision - precisionOf(result);
      BigInteger unscaled = result.movePointRight(expand + result.scale()).unscaledValue();
      result = new BigDecimal(unscaled, result.scale() + expand);
    } else if (precisionOf(result) > precision) {
      int contract = precisionOf(result) - precision;
      result = result.setScale(result.scale() - contract, RoundingMode.HALF_UP);
    }
    return result;
  }

  private static int precisionOf(BigDecimal value) {
    if (value.compareTo(BigDecimal.ZERO) == 0) {
      return value.scale() + 1;
    }
    return value.precision();
  }

  public static SciNum fromDigits(String digits, int exponent, int precision) {
    return new SciNum(new BigDecimal(new BigInteger(digits), -exponent), precision);
  }

  public static SciNum fromSmallSciNum(SmallSciNum smallSciNum) {
    return new SciNum(new BigDecimal(BigInteger.valueOf(smallSciNum.getUnscaled()), -smallSciNum.getExponent()), smallSciNum.getPrecision());
  }

  public static SciNum fromBigInteger(BigInteger bigInteger) {
    return new SciNum(new BigDecimal(bigInteger, 0), 0);
  }

  public static SciNum fromLong(long value) {
    return new SciNum(new BigDecimal(BigInteger.valueOf(value)), 0);
  }

  @TruffleBoundary
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (value.compareTo(BigDecimal.ZERO) == 0) {
      builder.repeat("0", value.scale() + 1);
    } else {
      builder.append(value.unscaledValue());
    }
    int offset = builder.charAt(0) == '-' ? 2 : 1;
    int exponent = builder.length() - offset - value.scale();
    if (builder.length() > 1) {
      builder.insert(offset, '.');
    }
    builder.append('e').append(exponent);
    return builder.toString();
  }

  public SciNum add(SciNum augend) {
    BigDecimal added = value.add(augend.value);
    int newScale = additiveScale(augend);
    BigDecimal result = added.setScale(newScale, RoundingMode.HALF_UP);
    return new SciNum(result, precisionOf(result));
  }

  private int additiveScale(SciNum other) {
    if (isExact) {
      return other.value.scale();
    } else if (other.isExact) {
      return value.scale();
    }
    return Math.min(value.scale(), other.value.scale());
  }

  public SciNum subtract(SciNum subtrahend) {
    BigDecimal subtracted = value.subtract(subtrahend.value);
    BigDecimal result = subtracted.setScale(additiveScale(subtrahend), RoundingMode.HALF_UP);
    return new SciNum(result, precisionOf(result));
  }

  public SciNum multiply(SciNum multiplicand) {
    int newPrecision = multiplicativePrecision(multiplicand);
    MathContext context = new MathContext(newPrecision);
    return new SciNum(value.multiply(multiplicand.value, context), newPrecision);
  }

  public SciNum mod(SciNum modulus) {
    BigDecimal remainder = value.remainder(modulus.value);
    if (remainder.signum() < 0) {
      remainder = remainder.add(modulus.value.abs());
    }
    int newPrecision = multiplicativePrecision(modulus);
    return new SciNum(remainder, newPrecision);
  }

  public Object truncateDivide(SciNum divisor) {
    BigDecimal result = value.divideToIntegralValue(divisor.value);
    try {
      return result.longValueExact();
    } catch (ArithmeticException e) {
      return new BigNumber(result.toBigInteger());
    }
  }

  public SciNum divide(SciNum divisor) {
    int precision = multiplicativePrecision(divisor);
    MathContext context = new MathContext(precision);
    BigDecimal quotient = value.divide(divisor.value, context);
    return new SciNum(quotient, precision);
  }

  private int multiplicativePrecision(SciNum other) {
    if (isExact && other.isExact) {
      return  6;
    } else if (isExact) {
      return  precisionOf(other.value);
    } else if (other.isExact) {
      return  precisionOf(value);
    }
    return Math.min(precisionOf(value), precisionOf(other.value));
  }

  public int compareTo(SciNum right) {
    return value.compareTo(right.value);
  }

  public SciNum squareRoot() {
    int precision = isExact ? 6 : precisionOf(value);
    BigDecimal sqrt = value.sqrt(new MathContext(precision));
    return new SciNum(sqrt, precision);
  }

  public SciNum negate() {
    return new SciNum(value.negate(), precisionOf(value));
  }

  public int compareTo(Rational right) {
    return value.multiply(new BigDecimal(right.denominator(), 0)).compareTo(new BigDecimal(right.numerator(), 0));
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
