package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.TailspinArray;

@GenerateInline(false)
public abstract class ArrayTypeMatcherNode extends MatcherNode {

  @Specialization
  protected boolean isArray(TailspinArray ignored) {
    return true;
  }

  @Fallback
  protected boolean notArray(Object ignored) { return false; }

  public static ArrayTypeMatcherNode create() {
    return ArrayTypeMatcherNodeGen.create();
  }
}
