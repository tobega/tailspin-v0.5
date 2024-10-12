package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.matchers.MeasureTypeMatcher;
import tailspin.language.runtime.Measure;

@NodeChild(value = "value", type = ValueNode.class)
public abstract class MeasureLiteral extends ValueNode {
  private final TruffleString unit;

  public MeasureLiteral(TruffleString unit) {
    this.unit = unit;
  }

  public static MeasureLiteral create(ValueNode value, TruffleString unit) {
    return MeasureLiteralNodeGen.create(unit, value);
  }

  @Specialization
  Object doCast(Measure value) {
    return new Measure(value.value(), unit);
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
