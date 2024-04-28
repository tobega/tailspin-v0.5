package tailspin.language.nodes.array;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import java.util.List;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.ResultIterator;
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
    ResultIterator collector = ResultIterator.empty();
    for (ValueNode content : this.contents) {
      collector = (ResultIterator) ResultIterator.merge(collector, content.executeGeneric(frame));
    }
    return TailspinArray.value(collector.getArray(), collector.getEnd());
  }
}
