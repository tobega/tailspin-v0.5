package tailspin.language.runtime;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleString.FromCodePointNode;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.api.strings.TruffleStringIterator;

public final class TailspinStrings {
  private static final TruffleString.Encoding TAILSPIN_STRING_ENCODING = TruffleString.Encoding.UTF_16;
  public static final TruffleString EMPTY = TAILSPIN_STRING_ENCODING.getEmpty();

  public static TruffleString concat(TruffleString s1, TruffleString s2, TruffleString.ConcatNode concatNode) {
    return concatNode.execute(s1, s2, TAILSPIN_STRING_ENCODING, true);
  }

  public static TruffleString fromJavaString(String value) {
    return TruffleString.fromJavaStringUncached(value, TAILSPIN_STRING_ENCODING);
  }

  public static TruffleString fromJavaString(String value, TruffleString.FromJavaStringNode fromJavaStringNode) {
    return fromJavaStringNode.execute(value, TAILSPIN_STRING_ENCODING);
  }

  public static TruffleStringIterator getCodePointIterator(TruffleString ts, TruffleString.CreateCodePointIteratorNode createCodePointIteratorNode) {
    return createCodePointIteratorNode.execute(ts, TAILSPIN_STRING_ENCODING);
  }

  public static String escapeAndQuote(TruffleString ts) {
    String s = ts.toJavaStringUncached();
    return "'" + s.replace("'", "''").replace("$", "$$") + "'";
  }

  public static TruffleStringBuilder newBuilder() {
    return TruffleStringBuilder.create(TAILSPIN_STRING_ENCODING);
  }

  public static TruffleString fromCodepoint(int codepoint, FromCodePointNode fromCodePointNode) {
    return fromCodePointNode.execute(codepoint, TAILSPIN_STRING_ENCODING);
  }
}
