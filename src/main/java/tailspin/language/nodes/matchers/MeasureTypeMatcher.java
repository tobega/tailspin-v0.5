package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.Measure;

@GenerateInline(value = false)
public abstract class MeasureTypeMatcher extends MatcherNode {
  final Object unit;

  protected MeasureTypeMatcher(Object unit, SourceSection sourceSection) {
    super(sourceSection);
    this.unit = unit;
  }

  @Specialization
  boolean doMeasure(Measure toMatch) {
    return unit == null || unit == toMatch.unit();
  }

  @Specialization
  boolean notMatcher(Object ignored) {
    return false;
  }

  public static MeasureTypeMatcher create(Object unit, SourceSection sourceSection) {
    return MeasureTypeMatcherNodeGen.create(unit, sourceSection);
  }
}
