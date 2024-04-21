package tailspin.language.nodes.transform;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.ResultIterator;

@NodeChild(value = "previousNode", type = ValueNode.class)
@NodeChild(value = "resultsNode", type = TransformNode.class)
public abstract class MergeResultNode extends TransformNode {
  @Specialization(guards = "previous == null")
  Object doFirst(Object previous, Object result) {
    return result;
  }

  @Specialization(guards = "result == null")
  Object doNothing(Object previous, Object result) {
    return previous;
  }

  @Specialization
  ResultIterator doMerge(Object previous, Object result) {
    ResultIterator merged;
    if (previous instanceof ResultIterator ri) {
      merged = ri;
    } else {
      merged = ResultIterator.empty();
      merged.addObject(previous);
    }
    if (result instanceof ResultIterator ri) {
      merged.add(ri);
    } else {
      merged.addObject(result);
    }
    return merged;
  }

  static class GetStaticObjectNode extends ValueNode {
    private final int slot;

    GetStaticObjectNode(int slot) {
      this.slot = slot;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
      return frame.getObjectStatic(slot);
    }
  }

  public static MergeResultNode create(int slot, TransformNode transformNode) {
    return MergeResultNodeGen.create(new GetStaticObjectNode(slot), transformNode);
  }
}
