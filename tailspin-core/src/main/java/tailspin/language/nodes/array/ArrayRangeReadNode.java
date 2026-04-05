package tailspin.language.nodes.array;

import static tailspin.language.runtime.Templates.LENS_CONTEXT_SLOT;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.RangeIteration;
import tailspin.language.nodes.value.GetContextFrameNode;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;
import tailspin.language.runtime.IndexedArrayValue;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.stream.ListStream;

@NodeChild(value = "array", type = ValueNode.class)
public abstract class ArrayRangeReadNode extends ValueNode {

  protected ArrayRangeReadNode(RangeIteration iterationNode, int resultSlot,
      SourceSection sourceSection) {
    super(sourceSection);
    this.iterationNode = iterationNode;
    this.resultSlot = resultSlot;
  }

  public abstract Object executeDirect(VirtualFrame frame, Object array);

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  RangeIteration iterationNode;

  final int resultSlot;

  @Specialization
  protected Object doIndexed(VirtualFrame frame, IndexedArrayValue indexedArrayValue,
      @Cached(inline = true, neverDefault = true) GetContextFrameNode getFrame,
      @Cached(inline = true) WriteLocalValueNode writeIndex) {
    VirtualFrame contextFrame = getFrame.execute(frame, this, indexedArrayValue.indexVar().getLevel());
    writeIndex.executeGeneric(contextFrame, this, indexedArrayValue.indexVar().getSlot(), indexedArrayValue.index());
    Object result = executeDirect(frame, indexedArrayValue.value());
    return indexedArrayValue.withValue(result);
  }

  @Specialization
  protected Object doArray(VirtualFrame frame, TailspinArray array) {
    frame.setObjectStatic(LENS_CONTEXT_SLOT, array);
    frame.setObjectStatic(resultSlot, new ListStream());
    iterationNode.executeTransform(frame);
    return frame.getObjectStatic(resultSlot);
  }

  @Specialization
  @SuppressWarnings("unchecked")
  protected Object doMultiSelect(VirtualFrame frame, ListStream multiple) {
    Object[] arrays = multiple.getArray();
    int size = multiple.size();
    for (int i = 0; i < size; i++) {
      arrays[i] = executeDirect(frame, arrays[i]);
    }
    return multiple;
  }

  @Specialization(guards = "interop.hasArrayElements(array)", limit = "3")
  @SuppressWarnings("unused")
  protected Object doInteropArray(VirtualFrame frame, Object array,
      @CachedLibrary("array") InteropLibrary interop) {
    frame.setObjectStatic(LENS_CONTEXT_SLOT, array);
    frame.setObjectStatic(resultSlot, new ListStream());
    iterationNode.executeTransform(frame);
    return frame.getObjectStatic(resultSlot);
  }

  @Specialization
  protected Object doIllegal(Object receiver) {
    throw new TypeError(String.format("Cannot read %s by range", receiver.getClass()), this);
  }

  public static ArrayRangeReadNode create(RangeIteration iteration, int resultSlot, ValueNode array,
      SourceSection sourceSection) {
    iteration.setResultSlot(resultSlot);
    return ArrayRangeReadNodeGen.create(iteration, resultSlot, sourceSection, array);
  }
}
