package tailspin.language.nodes.array;

import static tailspin.language.runtime.Templates.LENS_CONTEXT_SLOT;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.ArrayList;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.RangeIteration;
import tailspin.language.runtime.TailspinArray;

@NodeChild(value = "array", type = ValueNode.class)
public abstract class ArrayRangeReadNode extends ValueNode {

  protected ArrayRangeReadNode(RangeIteration iterationNode, int resultSlot) {
    this.iterationNode = iterationNode;
    this.resultSlot = resultSlot;
  }

  public abstract Object executeDirect(VirtualFrame frame, Object array);

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  RangeIteration iterationNode;

  final int resultSlot;

  @Specialization
  protected Object doArray(VirtualFrame frame, TailspinArray array) {
    frame.setObject(LENS_CONTEXT_SLOT, array);
    frame.setObjectStatic(resultSlot, new ArrayList<>());
    iterationNode.executeTransform(frame);
    return frame.getObjectStatic(resultSlot);
  }

  @Specialization
  @SuppressWarnings("unchecked")
  protected Object doMultiSelect(VirtualFrame frame, ArrayList<?> multiple) {
    ((ArrayList<Object>) multiple).replaceAll(array -> executeDirect(frame, array));
    return multiple;
  }

  @Specialization
  protected Object doIllegal(Object receiver) {
    throw new TypeError(String.format("Cannot read %s by range", receiver.getClass()));
  }

  public static ArrayRangeReadNode create(RangeIteration iteration, int resultSlot, ValueNode array) {
    iteration.setResultSlot(resultSlot);
    return ArrayRangeReadNodeGen.create(iteration, resultSlot, array);
  }
}
