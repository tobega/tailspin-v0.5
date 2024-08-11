package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.transform.AppendResultNode;
import tailspin.language.runtime.TailspinArray;

@NodeChild(value = "value", type = ValueNode.class)
public abstract class StreamNode extends TransformNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private AppendResultNode appendResultNode = AppendResultNode.create();

  @Specialization
  void doArray(VirtualFrame frame, TailspinArray ta) {
    appendResultNode.execute(frame, getResultSlot(), ta.stream());
  }

  public static StreamNode create(ValueNode value) {
    return StreamNodeGen.create(value);
  }
}
