package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.TruffleObject;

@ValueType
public record Measure(Object value, Object unit) implements TruffleObject {

  public static final Object SCALAR = "1";

  @Override
  public String toString() {
    return value.toString() + "\"" + unit + "\"";
  }
}
