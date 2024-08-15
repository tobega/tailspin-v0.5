package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.TruffleObject;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@ValueType
public class SciNum implements TruffleObject {

  private final BigDecimal value;
  private final MathContext precision;

  public SciNum(BigDecimal value, MathContext precision) {
    this.value = value;
    this.precision = precision;
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
    BigDecimal result = added.setScale(Math.min(value.scale(), augend.value.scale()), RoundingMode.HALF_UP);
    return new SciNum(result, new MathContext(6));
  }

  public SciNum subtract(SciNum augend) {
    BigDecimal subtracted = value.subtract(augend.value);
    BigDecimal result = subtracted.setScale(Math.min(value.scale(), augend.value.scale()), RoundingMode.HALF_UP);
    return new SciNum(result, new MathContext(6));
  }
}
