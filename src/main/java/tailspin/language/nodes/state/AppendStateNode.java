package tailspin.language.nodes.state;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import java.util.ArrayList;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinArray;

@NodeChild(value = "target", type = ValueNode.class)
@NodeChild(value = "value", type = ValueNode.class)
public abstract class AppendStateNode extends ValueNode {

  public AppendStateNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  public abstract Object executeDirect(Object target, Object value);

  @Specialization
  protected Object appendArrayMany(TailspinArray array, ArrayList<?> values) {
    TailspinArray result = array.getThawed();
    for (Object value : values) {
      executeDirect(result, value);
    }
    return result;
  }

  @Specialization
  TailspinArray appendArray(TailspinArray target, Object value) {
    TailspinArray result = target.getThawed();
    result.append(value);
    return result;
  }

  @Specialization
  Object cannotAppend(Object target, Object ignored) {
    throw new TypeError("Cannot append to " + target, this);
  }

  public static AppendStateNode create(ValueNode target, ValueNode value,
      SourceSection sourceSection) {
    return AppendStateNodeGen.create(sourceSection, target, value);
  }
}
