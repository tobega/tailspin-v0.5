package tailspin.language.nodes.array;

import static tailspin.language.runtime.Templates.LENS_CONTEXT_SLOT;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.stream.ListStream;

@NodeChild(value = "array", type = ValueNode.class)
@NodeChild(value = "value", type = ValueNode.class)
public abstract class ArrayMutateNode extends ValueNode {

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  ValueNode lensNode;

  public ArrayMutateNode(SourceSection sourceSection, ValueNode lensNode) {
    super(sourceSection);
    this.lensNode = lensNode;
  }

  public abstract Object executeDirect(VirtualFrame frame, Object target, Object value);

  public static ArrayMutateNode create(SourceSection sourceSection, ValueNode index, ValueNode array,
      ValueNode value) {
    return ArrayMutateNodeGen.create(sourceSection, index, array, value);
  }

  @Specialization
  protected Object doSimple(VirtualFrame frame, TailspinArray array, Object value,
      @Cached DoArrayWriteNode arrayWriteNode) {
    TailspinArray result = array.getThawed();
    frame.setObjectStatic(LENS_CONTEXT_SLOT, result);
    arrayWriteNode.executeDirect(result, lensNode.executeGeneric(frame), value);
    frame.clearObjectStatic(LENS_CONTEXT_SLOT);
    return result;
  }

  @Specialization(guards = "arrayStream.size() == valueStream.size()")
  @SuppressWarnings("unchecked")
  protected Object doMany(VirtualFrame frame, ListStream arrayStream, ListStream valueStream) {
    Object[] arrays = arrayStream.getArray();
    Object[] values = valueStream.getArray();
    int size = arrayStream.size();
    for (int i = 0; i < size; i++) {
      arrays[i] = executeDirect(frame, arrays[i], values[i]);
    }
    return arrayStream;
  }

  @Specialization
  @SuppressWarnings("unused")
  protected Object doIllegal(VirtualFrame frame, Object receiver, Object value) {
    CompilerDirectives.transferToInterpreterAndInvalidate();
    throw new TypeError(String.format("Cannot access %s by %s to set %s", receiver.getClass(), lensNode.executeGeneric(frame).getClass(), value.getClass()),
        this);
  }
}
