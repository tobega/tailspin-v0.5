package tailspin.language.nodes.array;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import java.util.ArrayList;
import java.util.List;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinArray;

public class ArrayLiteral extends ValueNode {
  @Children
  private final ValueNode[] contents;

  private ArrayLiteral(List<ValueNode> contents) {
    this.contents = contents.toArray(new ValueNode[0]);
  }

  public static ArrayLiteral create(List<ValueNode> contents) {
    return new ArrayLiteral(contents);
  }

  @Override
  @ExplodeLoop
  public Object executeGeneric(VirtualFrame frame) {
    ArrayList<Object> collector = new ArrayList<>();
    for (ValueNode content : this.contents) {
      Object value = content.executeGeneric(frame);
      if (value instanceof ArrayList<?> values) {
        collector.addAll(values);
      } else {
        collector.add(value);
      }
    }
    return TailspinArray.value(collector.toArray());
  }
}
