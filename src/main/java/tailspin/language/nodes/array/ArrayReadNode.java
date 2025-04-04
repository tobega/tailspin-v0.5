package tailspin.language.nodes.array;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;
import java.util.ArrayList;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.GetContextFrameNode;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;
import tailspin.language.runtime.BigNumber;
import tailspin.language.runtime.IndexedArrayValue;
import tailspin.language.runtime.Reference;
import tailspin.language.runtime.TailspinArray;

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

  @GenerateInline(value = false)
  public static abstract class DoArrayReadNode extends Node {
    final boolean noFail;

    protected DoArrayReadNode(boolean noFail) {
      this.noFail = noFail;
    }

    public abstract Object executeArrayRead(TailspinArray array, Object index, Reference indexVar);

    @Specialization
    protected Object doLong(TailspinArray array, long index, Reference indexVar) {
      Object value = array.getNative((int) index - 1, noFail);
      if (indexVar == null) {
        return value;
      } else {
        return new IndexedArrayValue(indexVar, index, value);
      }
    }

    @Specialization
    protected Object doBigNumber(TailspinArray array, BigNumber index, Reference indexVar) {
      Object value = array.getNative(index.intValueExact() - 1, noFail);
      if (indexVar == null) {
        return value;
      } else {
        return new IndexedArrayValue(indexVar, index, value);
      }
    }

    @Specialization
    protected Object doArray(TailspinArray array, TailspinArray selection, Reference indexVar) {
      long length = selection.getArraySize();
      ArrayList<Object> elements = new ArrayList<>();
      for (int i = 0; i < length; i++) {
        elements.add(executeArrayRead(array, selection.getNative(i, false), indexVar));
      }
      return elements;
    }

    @Specialization
    protected Object doRange(TailspinArray array, ArrayList<?> range, Reference indexVar) {
      ArrayList<Object> elements = new ArrayList<>();
      for (Object o : range) {
        elements.add(executeArrayRead(array, o, indexVar));
      }
      return elements;
    }
  }

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
  protected Object doMultiSelect(VirtualFrame frame, ArrayList<?> multiple) {
    ((ArrayList<Object>) multiple).replaceAll(array -> executeDirect(frame, array));
    return multiple;
  }

  @Specialization
  protected Object doSimpleRead(VirtualFrame frame, TailspinArray array,
      @Cached(parameters = "noFail") DoArrayReadNode arrayReadNode) {
    return arrayReadNode.executeArrayRead(array, lensNode.executeGeneric(frame), indexVar);
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
