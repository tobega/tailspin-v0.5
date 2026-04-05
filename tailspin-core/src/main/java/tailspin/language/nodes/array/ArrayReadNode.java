package tailspin.language.nodes.array;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.GetContextFrameNode;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;
import tailspin.language.runtime.IndexedArrayValue;
import tailspin.language.runtime.Reference;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.stream.ListStream;

@NodeChild(value = "array", type = ValueNode.class)
public abstract class ArrayReadNode extends ValueNode {
  final boolean noFail;
  final Reference indexVar;

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  ValueNode lensNode;

  protected ArrayReadNode(boolean noFail, Reference indexVar, ValueNode lensNode,
      SourceSection sourceSection) {
    super(sourceSection);
    this.noFail = noFail;
    this.indexVar = indexVar;
    this.lensNode = lensNode;
  }

  public abstract Object executeDirect(VirtualFrame frame, Object array);

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
  @SuppressWarnings("unchecked")
  protected Object doMultiSelect(VirtualFrame frame, ListStream multiple) {
    Object[] arrays = multiple.getArray();
    int size = multiple.size();
    for (int i = 0; i < size; i++) {
      arrays[i] = executeDirect(frame, arrays[i]);
    }
    return multiple;
  }

  @Specialization
  protected Object doSimpleRead(VirtualFrame frame, TailspinArray array,
      @Cached(parameters = "noFail") DoArrayReadNode arrayReadNode) {
    return arrayReadNode.executeArrayRead(array, lensNode.executeGeneric(frame), indexVar);
  }

  @Specialization(guards = "interop.hasArrayElements(array)", limit = "3")
  protected Object doInteropRead(VirtualFrame frame, Object array,
      @Cached(parameters = "noFail") DoInteropArrayReadNode arrayReadNode,
      @CachedLibrary("array") InteropLibrary interop) {
    return arrayReadNode.executeInteropRead(array, lensNode.executeGeneric(frame), indexVar);
  }

  @Specialization
  protected Object doIllegal(VirtualFrame ignored, Object notArray) {
    throw new TypeError("Cannot read " + notArray + " by array lens", this);
  }

  public static ArrayReadNode create(boolean noFail, ValueNode array, ValueNode lens, Reference indexVar,
      SourceSection sourceSection) {
    return ArrayReadNodeGen.create(noFail, indexVar, lens, sourceSection, array);
  }
}
