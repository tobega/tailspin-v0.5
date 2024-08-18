package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.TruffleObject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

@ValueType
public class SciNum implements TruffleObject {
  private final BigDecimal value;
  private final boolean isExact;

  private SciNum(BigDecimal value, boolean isExact) {
    this.value = value;
    this.isExact = isExact;
  }

  public SciNum(BigDecimal value) {
    this(value, false);
  }

  public static SciNum fromBigNumber(BigNumber bigNumber) {
    return new SciNum(new BigDecimal(bigNumber.asBigInteger(), 0), true);
  }

  public static SciNum fromLong(long value) {
    return new SciNum(new BigDecimal(BigInteger.valueOf(value)), true);
  }

  @TruffleBoundary
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(value.unscaledValue());
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
    BigDecimal result = added.setScale(additiveScale(augend), RoundingMode.HALF_UP);
    return new SciNum(result);
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
    return new SciNum(result);
  }

  public SciNum multiply(SciNum multiplicand) {
    MathContext context = new MathContext(multiplicativePrecision(multiplicand));
    return new SciNum(value.multiply(multiplicand.value, context));
  }

  public SciNum mod(SciNum modulus) {
    BigDecimal remainder = value.remainder(modulus.value);
    if (remainder.signum() < 0) {
      remainder = remainder.add(modulus.value.abs());
    }
    return new SciNum(remainder);
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
    quotient = fixPrecision(quotient, precision);
    return new SciNum(quotient);
  }

  private static BigDecimal fixPrecision(BigDecimal result, int precision) {
    if (result.precision() < precision) {
      int expand = precision - result.precision();
      BigInteger unscaled = result.movePointRight(expand + result.scale()).unscaledValue();
      result = new BigDecimal(unscaled, result.scale() + expand);
    }
    return result;
  }

  private int multiplicativePrecision(SciNum other) {
    if (isExact && other.isExact) {
      return  6;
    } else if (isExact) {
      return  other.value.precision();
    } else if (other.isExact) {
      return  value.precision();
    }
    return Math.min(value.precision(), other.value.precision());
  }

  public int compareTo(SciNum right) {
    return value.compareTo(right.value);
  }

  public SciNum squareRoot() {
    int precision = isExact ? 6 : value.precision();
    BigDecimal sqrt = value.sqrt(new MathContext(precision));
    return new SciNum(fixPrecision(sqrt, precision));
  }
}
