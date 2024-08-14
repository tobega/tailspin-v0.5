package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.TruffleObject;
import java.math.BigDecimal;
import java.math.MathContext;

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
}
