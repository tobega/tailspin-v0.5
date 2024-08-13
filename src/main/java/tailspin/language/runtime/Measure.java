package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.TruffleObject;
import tailspin.language.nodes.TailspinTypes;

@ValueType
public record Measure(Object value, String unit) implements TruffleObject {

  public static final String SCALAR = "1";

  @Override
  public String toString() {
    return value.toString() + "\"" + unit + "\"";
  }

  public boolean isLong() {
    return value instanceof Long;
  }

  public BigNumber bigNumber() {
    if (isLong())
      return TailspinTypes.castBigNumber((Long) value);
    return (BigNumber) value;
  }
}
