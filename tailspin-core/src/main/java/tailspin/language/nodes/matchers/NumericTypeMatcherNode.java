package tailspin.language.nodes.matchers;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SciNum;
import tailspin.language.runtime.SmallSciNum;

@GenerateInline(false)
public abstract class NumericTypeMatcherNode extends MatcherNode {

  public NumericTypeMatcherNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  @Specialization
  protected boolean isLong(long ignored) {
    return true;
  }

  @Specialization
  protected boolean isBigNumber(BigNumber ignored) {
    return true;
  }

  @Specialization
  protected boolean isRational(Rational ignored) {
    return true;
  }

  @Specialization
  protected boolean isSmallSciNum(SmallSciNum ignored) {
    return true;
  }

  @Specialization
  protected boolean isSciNum(SciNum ignored) {
    return true;
  }

  @Fallback
  protected boolean notNumber(Object ignored) { return false; }

  public static NumericTypeMatcherNode create() {
    return NumericTypeMatcherNodeGen.create(INTERNAL_CODE_SOURCE);
  }
}
