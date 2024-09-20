package tailspin.language.nodes.value;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Exclusive;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.ArrayList;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.transform.AppendResultNode.MergeResultNode;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;
import tailspin.language.runtime.IndexedArrayValue;
import tailspin.language.runtime.TailspinArray;

@NodeChild(type = ValueNode.class)
public abstract class TransformLensNode extends ValueNode {

  private final int cvSlot;

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private TransformNode transformNode;

  private final int resultSlot;

  protected TransformLensNode(int cvSlot, TransformNode transformNode, int resultSlot) {
    this.cvSlot = cvSlot;
    this.transformNode = transformNode;
    this.resultSlot = resultSlot;
    transformNode.setResultSlot(resultSlot);
  }

  public abstract Object executeDirect(VirtualFrame frame, Object value);

  @Specialization
  @SuppressWarnings("unchecked")
  TailspinArray doTransformMany(VirtualFrame frame, ArrayList<?> many,
      @Cached(inline = true) MergeResultNode mergeResultNode) {
    ArrayList<Object> elements = new ArrayList<>();
    for (Object one : many) {
      elements = (ArrayList<Object>) mergeResultNode.execute(this, elements,
          executeDirect(frame, one));
    }
    return TailspinArray.value(elements.toArray());
  }

  @Specialization
  Object doTransformIndexed(VirtualFrame frame, IndexedArrayValue indexedArrayValue,
      @Cached(inline = true, neverDefault = true) GetContextFrameNode getFrame,
      @Cached(inline = true) @Exclusive WriteLocalValueNode writeIndex) {
    VirtualFrame contextFrame = getFrame.execute(frame, this, indexedArrayValue.indexVar().getLevel());
    writeIndex.executeGeneric(contextFrame, this, indexedArrayValue.indexVar().getSlot(), indexedArrayValue.index());
    return executeDirect(frame, indexedArrayValue.value());
  }

  @Specialization
  Object doTransformOne(VirtualFrame frame, Object one,
      @Cached(inline = true) @Exclusive WriteLocalValueNode writeLocalValueNode) {
    writeLocalValueNode.executeGeneric(frame, this, cvSlot, one);
    transformNode.executeTransform(frame);
    Object result = frame.getObjectStatic(resultSlot);
    frame.setObjectStatic(resultSlot, null);
    return result;
  }

  public static TransformLensNode create(ValueNode lensResult, int cvSlot, TransformNode transform, int resultSlot) {
    return TransformLensNodeGen.create(cvSlot, transform, resultSlot, lensResult);
  }
}
