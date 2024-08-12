package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Measure;

@NodeChild(value = "value", type = ValueNode.class)
public abstract class MeasureLiteral extends ValueNode {
  private final String unit;

  public MeasureLiteral(String unit) {
    this.unit = unit;
  }

  public static MeasureLiteral create(ValueNode value, String unit) {
    return MeasureLiteralNodeGen.create(unit, value);
  }

  @Specialization
  Object doMeasure(Object value) {
    return new Measure(value, unit);
  }
}
