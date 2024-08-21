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
  private final BigDecimal bigValue;
  private final boolean isExact;
  private final double smallValue;
  private final int precision;

  private SciNum(BigDecimal bigValue, boolean isExact) {
    this.bigValue = bigValue;
    this.isExact = isExact;
    smallValue = 0;
    precision = 0;
  }

  private SciNum(double smallValue, int precision) {
    this.bigValue = null;
    this.isExact = false;
    this.smallValue = smallValue;
    this.precision = precision;
  }

  public static SciNum fromDigits(String digits, int exponent) {
    String sign = "";
    if (digits.startsWith("-") || digits.startsWith("+")) {
      sign = digits.substring(0, 1);
      digits = digits.substring(1);
    }
    int precision = digits.length();
    while (digits.startsWith("0")) digits = digits.substring(1);
    boolean isExact = digits.isEmpty();
    if (!isExact) precision = digits.length();
    if (precision <= 15 && Math.abs(exponent) < 1020) {
      double smallValue = Double.parseDouble(sign + digits + "e" + exponent);
      return new SciNum(smallValue, precision);
    }
    return new SciNum(new BigDecimal(new BigInteger(sign + digits), -exponent), isExact);
  }

  private SciNum(BigDecimal bigValue) {
    this(bigValue, bigValue.compareTo(BigDecimal.ZERO) == 0);
  }

  public static SciNum fromBigNumber(BigNumber bigNumber) {
    return new SciNum(new BigDecimal(bigNumber.asBigInteger(), 0), true);
  }

  public static SciNum fromLong(long value) {
    return new SciNum(new BigDecimal(BigInteger.valueOf(value)), true);
  }

  private SciNum asBigValue() {
    if (bigValue != null) return this;
    BigDecimal value = BigDecimal.valueOf(smallValue);
    return new SciNum(fixPrecision(value, precision));
  }

  private static SciNum maybeSmallValue(BigDecimal bigValue) {
    if (bigValue.compareTo(BigDecimal.ZERO) != 0 && bigValue.precision() <= 15 && Math.abs(bigValue.scale()) < 1020) {
      return new SciNum(bigValue.doubleValue(), bigValue.precision());
    }
    return new SciNum(bigValue);
  }

  @TruffleBoundary
  @Override
  public String toString() {
    BigDecimal value = bigValue;
    if (value == null) {
      value = asBigValue().bigValue;
    }
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
    if (bigValue != null) return bigAdd(augend.asBigValue());
    if (augend.bigValue != null) return asBigValue().bigAdd(augend);
    double result = smallValue + augend.smallValue;
    int precision = getAdditivePrecision(Math.getExponent(result), augend);
    return new SciNum(result, precision);
  }

  private int getAdditivePrecision(int e, SciNum augend) {
    int precision;
    int e0 = Math.getExponent(smallValue);
    int e1 = Math.getExponent(augend.smallValue);
    if (e0 < e1) {
      precision = (int) (Math.min((this.precision + Math.round((e1 - e0) / 3.3)), augend.precision) + Math.round((e - e1) / 3.3));
    } else {
      precision = (int) (Math.min(this.precision, (augend.precision + Math.round((e0 - e1) / 3.3))) + Math.round((e - e0) / 3.3));
    }
    return precision;
  }

  private SciNum bigAdd(SciNum augend) {
    BigDecimal added = bigValue.add(augend.bigValue);
    BigDecimal result = added.setScale(additiveScale(augend), RoundingMode.HALF_UP);
    return maybeSmallValue(result);
  }

  private int additiveScale(SciNum other) {
    if (isExact) {
      return other.bigValue.scale();
    } else if (other.isExact) {
      return bigValue.scale();
    }
    return Math.min(bigValue.scale(), other.bigValue.scale());
  }

  public SciNum subtract(SciNum subtrahend) {
    if (bigValue != null) return bigSubtract(subtrahend.asBigValue());
    if (subtrahend.bigValue != null) return asBigValue().bigSubtract(subtrahend);
    double result = smallValue - subtrahend.smallValue;
    int precision = getAdditivePrecision(Math.getExponent(result), subtrahend);
    return new SciNum(result, precision);
  }

  private SciNum bigSubtract(SciNum subtrahend) {
    BigDecimal subtracted = bigValue.subtract(subtrahend.bigValue);
    BigDecimal result = subtracted.setScale(additiveScale(subtrahend), RoundingMode.HALF_UP);
    return maybeSmallValue(result);
  }

  public SciNum multiply(SciNum multiplicand) {
    if (bigValue != null) return bigMultiply(multiplicand.asBigValue());
    if (multiplicand.bigValue != null) return asBigValue().bigMultiply(multiplicand);
    double result = smallValue * multiplicand.smallValue;
    return new SciNum(result, Math.min(precision, multiplicand.precision));
  }

  private SciNum bigMultiply(SciNum multiplicand) {
    MathContext context = new MathContext(multiplicativePrecision(multiplicand));
    return maybeSmallValue(bigValue.multiply(multiplicand.bigValue, context));
  }

  public SciNum mod(SciNum modulus) {
    if (bigValue != null) return bigMod(modulus.asBigValue());
    if (modulus.bigValue != null) return asBigValue().bigMod(modulus);
    double result = Math.IEEEremainder(smallValue, modulus.smallValue);
    if (result < 0) result += Math.abs(modulus.smallValue);
    return new SciNum(result, Math.min(precision, modulus.precision));
  }

  private SciNum bigMod(SciNum modulus) {
    BigDecimal remainder = bigValue.remainder(modulus.bigValue);
    if (remainder.signum() < 0) {
      remainder = remainder.add(modulus.bigValue.abs());
    }
    remainder = fixPrecision(remainder, multiplicativePrecision(modulus));
    return maybeSmallValue(remainder);
  }

  public Object truncateDivide(SciNum divisor) {
    return asBigValue().bigTruncateDivide(divisor.asBigValue());
  }

  private Object bigTruncateDivide(SciNum divisor) {
    BigDecimal result = bigValue.divideToIntegralValue(divisor.bigValue);
    try {
      return result.longValueExact();
    } catch (ArithmeticException e) {
      return new BigNumber(result.toBigInteger());
    }
  }

  public SciNum divide(SciNum divisor) {
    if (bigValue != null) return bigDivide(divisor.asBigValue());
    if (divisor.bigValue != null) return asBigValue().divide(divisor);
    double result = smallValue / divisor.smallValue;
    return new SciNum(result, Math.min(precision, divisor.precision));
  }

  private SciNum bigDivide(SciNum divisor) {
    int precision = multiplicativePrecision(divisor);
    MathContext context = new MathContext(precision);
    BigDecimal quotient = bigValue.divide(divisor.bigValue, context);
    quotient = fixPrecision(quotient, precision);
    return maybeSmallValue(quotient);
  }

  private static BigDecimal fixPrecision(BigDecimal result, int precision) {
    if (result.precision() < precision) {
      int expand = precision - result.precision();
      BigInteger unscaled = result.movePointRight(expand + result.scale()).unscaledValue();
      result = new BigDecimal(unscaled, result.scale() + expand);
    } else if (result.precision() > precision) {
      int contract = result.precision() - precision;
      result = result.setScale(result.scale() - contract, RoundingMode.HALF_UP);
    }
    return result;
  }

  private int multiplicativePrecision(SciNum other) {
    if (isExact && other.isExact) {
      return  6;
    } else if (isExact) {
      return  other.bigValue.precision();
    } else if (other.isExact) {
      return  bigValue.precision();
    }
    return Math.min(bigValue.precision(), other.bigValue.precision());
  }

  public int compareTo(SciNum right) {
    return asBigValue().bigValue.compareTo(right.asBigValue().bigValue);
  }

  public SciNum squareRoot() {
    if (bigValue != null) return bigSquareRoot();
    return new SciNum(Math.sqrt(smallValue), precision);
  }

  private SciNum bigSquareRoot() {
    int precision = isExact ? 6 : bigValue.precision();
    BigDecimal sqrt = bigValue.sqrt(new MathContext(precision));
    return maybeSmallValue(fixPrecision(sqrt, precision));
  }
}
