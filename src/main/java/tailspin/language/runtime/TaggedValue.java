package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.TruffleObject;

@ValueType
public record TaggedValue(VocabularyType type, Object value) implements TruffleObject {
  @Override
  public String toString() {
    return type.toString() + "´" + value.toString();
  }
}