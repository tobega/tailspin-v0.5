package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.TruffleObject;

@ValueType
public record Measure(Object value, String unit) implements TruffleObject {

  @Override
  public String toString() {
    return value.toString() + "\"" + unit + "\"";
  }
}
