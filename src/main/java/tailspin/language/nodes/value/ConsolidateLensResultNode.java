package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.ArrayList;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinArray;

@NodeChild(type = ValueNode.class)
public abstract class ConsolidateLensResultNode extends ValueNode {
  public abstract Object executeDirect(VirtualFrame frame, Object value);

  @Specialization
  TailspinArray doConsolidateMany(VirtualFrame frame, ArrayList<?> many) {
    Object[] elements = new Object[many.size()];
    for (int i = 0; i < many.size(); i++) {
      elements[i] = executeDirect(frame, many.get(i));
    }
    return TailspinArray.value(elements);
  }

  @Specialization
  Object doConsolidateOne(VirtualFrame ignored, Object one) {
    return one;
  }

  public static ConsolidateLensResultNode create(ValueNode lensResult) {
    return ConsolidateLensResultNodeGen.create(lensResult);
  }
}
