package tailspin.language.nodes.array;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.source.SourceSection;
import java.util.ArrayList;
import java.util.List;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.TailspinArray;

public class ArrayLiteral extends ValueNode {
  private final int buildSlot;
  @Children
  private final TransformNode[] contents;

  private ArrayLiteral(int buildSlot, List<TransformNode> contents, SourceSection sourceSection) {
    super(sourceSection);
    this.buildSlot = buildSlot;
    this.contents = contents.toArray(new TransformNode[0]);
    for (TransformNode content : contents) {
      content.setResultSlot(buildSlot);
    }
  }

  public static ArrayLiteral create(int buildSlot, List<TransformNode> contents,
      SourceSection sourceSection) {
    return new ArrayLiteral(buildSlot, contents, sourceSection);
  }

  @Override
  @ExplodeLoop
  public Object executeGeneric(VirtualFrame frame) {
    frame.setObjectStatic(buildSlot, new ArrayList<>());
    for (TransformNode content : this.contents) {
      content.executeTransform(frame);
    }
    ArrayList<?> collector = (ArrayList<?>) frame.getObjectStatic(buildSlot);
    frame.setObjectStatic(buildSlot, null);
    return TailspinArray.value(collector.toArray());
  }
}
