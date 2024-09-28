package tailspin.language.nodes.state;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import java.util.ArrayList;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinArray;

@NodeChild(value = "target", type = ValueNode.class)
@NodeChild(value = "value", type = ValueNode.class)
public abstract class AppendStateNode extends ValueNode {
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
    throw new TypeError("Cannot append to " + target);
  }

  public static AppendStateNode create(ValueNode target, ValueNode value) {
    return AppendStateNodeGen.create(target, value);
  }
}
