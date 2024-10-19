package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.regex.Pattern;
import tailspin.language.TypeError;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinStrings;

@NodeChild(value = "dummy", type = ValueNode.class)
@NodeChild(type = ValueNode.class)
public abstract class RegexMatcherNode extends MatcherNode {
  private final boolean isTypeChecked;

  protected RegexMatcherNode(boolean isTypeChecked) {
    this.isTypeChecked = isTypeChecked;
  }

  @Specialization
  boolean doRegex(TruffleString toMatch, TruffleString regex,
      @Cached TruffleString.ToJavaStringNode toJavaStringNode) {
    Pattern pattern = Pattern.compile(TailspinStrings.toJavaString(regex, toJavaStringNode));
    return pattern.matcher(TailspinStrings.toJavaString(toMatch, toJavaStringNode)).matches();
  }

  @Specialization
  @SuppressWarnings("unused")
  boolean doIllegal(Object toMatch, TruffleString regex) {
    if (!isTypeChecked) throw new TypeError("Can't regex match " + toMatch);
    return false;
  }

  public static RegexMatcherNode create(boolean isTypeChecked, ValueNode valueNode) {
    return RegexMatcherNodeGen.create(isTypeChecked, null, valueNode);
  }
}
