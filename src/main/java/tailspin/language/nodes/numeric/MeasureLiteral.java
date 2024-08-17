package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.matchers.MeasureTypeMatcher;
import tailspin.language.runtime.Measure;

@NodeChild(value = "value", type = ValueNode.class)
public abstract class MeasureLiteral extends ValueNode {
  private final Object unit;

  public MeasureLiteral(Object unit) {
    this.unit = unit;
  }

  public static MeasureLiteral create(ValueNode value, Object unit) {
    return MeasureLiteralNodeGen.create(unit, value);
  }

  @Specialization
  Object doMeasure(Object value) {
    return new Measure(value, unit);
  }

  @Override
  public MatcherNode getTypeMatcher() {
    return MeasureTypeMatcher.create(unit);
  }
}
