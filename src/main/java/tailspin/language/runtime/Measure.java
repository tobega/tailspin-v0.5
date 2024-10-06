package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.strings.TruffleString;

@ValueType
public record Measure(Object value, TruffleString unit) implements TruffleObject {

  public static final TruffleString SCALAR = TailspinStrings.fromJavaString("1");

  @Override
  public String toString() {
    return value.toString() + "\"" + unit + "\"";
  }
}
