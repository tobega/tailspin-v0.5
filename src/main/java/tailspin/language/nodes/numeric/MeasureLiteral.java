package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Measure;

@NodeChild(value = "value", type = ValueNode.class)
public abstract class MeasureLiteral extends ValueNode {
  private final TruffleString unit;

  public MeasureLiteral(TruffleString unit, SourceSection sourceSection) {
    super(sourceSection);
    this.unit = unit;
  }

  public static MeasureLiteral create(ValueNode value, TruffleString unit,
      SourceSection sourceSection) {
    return MeasureLiteralNodeGen.create(unit, sourceSection, value);
  }

  @Specialization
  Object doCast(Measure value) {
    return new Measure(value.value(), unit);
  }

  @Specialization
  Object doMeasure(Object value) {
    return new Measure(value, unit);
  }
}
