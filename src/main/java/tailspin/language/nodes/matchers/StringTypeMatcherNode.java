package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import tailspin.language.nodes.MatcherNode;

@GenerateInline(value = false)
public abstract class StringTypeMatcherNode extends MatcherNode {
  @Specialization
  boolean doString(TruffleString ignored) {
    return true;
  }

  @Specialization
  boolean doOther(Object ignored) {
    return false;
  }

  public static StringTypeMatcherNode create() {
    return StringTypeMatcherNodeGen.create();
  }
}
