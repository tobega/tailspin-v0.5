package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.ValueType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Set;

@ValueType
@ExportLibrary(InteropLibrary.class)
public record Measure(Object value, TruffleString unit) implements TruffleObject {

  public static final TruffleString SCALAR = TailspinStrings.fromJavaString("1");

  @Override
  public String toString() {
    return value.toString() + "\"" + unit + "\"";
  }

  @ExportMessage
  public boolean hasMembers() {
    return true;
  }

  static final Set<String> supportedMessages = Set.of("raw");

  @ExportMessage
  public boolean isMemberReadable(String member) {
    return supportedMessages.contains(member);
  }

  @ExportMessage
  public Object readMember(String member) throws UnknownIdentifierException {
    return switch(member) {
      case "raw" -> value();
      default -> throw UnknownIdentifierException.create(member);
    };
  }

  @ExportMessage
  public Object getMembers(@SuppressWarnings("unused") boolean includeInternal) {
    return TailspinArray.value(new String[]{"raw"});
  }
}
