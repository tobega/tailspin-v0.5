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
    int exponent = builder.length() - 1 - value.scale();
    if (builder.length() > 1) builder.insert(1, '.');
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
    int resultPrecision = Math.min(value.precision(), multiplicand.value.precision());
    MathContext context = new MathContext(resultPrecision);
    return new SciNum(value.multiply(multiplicand.value, context));
  }
}
