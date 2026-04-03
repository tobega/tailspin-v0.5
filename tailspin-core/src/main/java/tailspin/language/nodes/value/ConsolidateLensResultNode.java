package tailspin.language.nodes.value;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.IndexedArrayValue;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.stream.ListStream;

@NodeChild(type = ValueNode.class)
public abstract class ConsolidateLensResultNode extends ValueNode {

  public ConsolidateLensResultNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  public abstract Object executeDirect(VirtualFrame frame, Object value);

  @Specialization
  TailspinArray doConsolidateMany(VirtualFrame frame, ListStream many) {
    Object[] result = many.getArray();
    Object[] elements = new Object[many.size()];
    int size = many.size();
    for (int i = 0; i < size; i++) {
      elements[i] = executeDirect(frame, result[i]);
    }
    return TailspinArray.value(elements);
  }

  @Specialization
  Object doConsolidateIndexed(VirtualFrame frame, IndexedArrayValue indexedArrayValue) {
    return executeDirect(frame, indexedArrayValue.value());
  }

  @Specialization
  Object doConsolidateOne(VirtualFrame ignored, Object one) {
    return one;
  }

  public static ConsolidateLensResultNode create(ValueNode lensResult) {
    return ConsolidateLensResultNodeGen.create(INTERNAL_CODE_SOURCE, lensResult);
  }
}
