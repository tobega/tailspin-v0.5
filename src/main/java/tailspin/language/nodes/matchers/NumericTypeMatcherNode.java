package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.BigNumber;

@GenerateInline(false)
public abstract class NumericTypeMatcherNode extends MatcherNode {

  @Specialization
  protected boolean isLong(long ignored) {
    return true;
  }

  @Specialization
  protected boolean isBigNumber(BigNumber ignored) {
    return true;
  }

  @Fallback
  protected boolean notStructure(Object ignored) { return false; }

  public static NumericTypeMatcherNode create() {
    return NumericTypeMatcherNodeGen.create();
  }
}
