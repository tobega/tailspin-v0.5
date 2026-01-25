package tailspin.language.nodes.matchers;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import tailspin.language.nodes.MatcherNode;

@GenerateInline(value = false)
public abstract class StringTypeMatcherNode extends MatcherNode {

  public StringTypeMatcherNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  @Specialization
  boolean doString(TruffleString ignored) {
    return true;
  }

  @Specialization
  boolean doOther(Object ignored) {
    return false;
  }

  public static StringTypeMatcherNode create() {
    return StringTypeMatcherNodeGen.create(INTERNAL_CODE_SOURCE);
  }
}
