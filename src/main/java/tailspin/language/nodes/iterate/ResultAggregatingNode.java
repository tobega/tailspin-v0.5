package tailspin.language.nodes.iterate;

import static com.oracle.truffle.api.CompilerDirectives.castExact;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.profiles.CountingConditionProfile;
import java.util.ArrayList;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;

public class ResultAggregatingNode extends TransformNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private ValueNode resultNode;
  final CountingConditionProfile previousNull = CountingConditionProfile.create();
  final CountingConditionProfile hasResult = CountingConditionProfile.create();
  final CountingConditionProfile previousStream = CountingConditionProfile.create();
  final CountingConditionProfile resultStream = CountingConditionProfile.create();

  ResultAggregatingNode(ValueNode resultNode) {
    this.resultNode = resultNode;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void executeTransform(VirtualFrame frame) {
    Object previous = frame.getObjectStatic(getResultSlot());
    Object result = resultNode.executeGeneric(frame);
    if (previousNull.profile(previous == null)) {
      frame.setObjectStatic(getResultSlot(), result);
    } else if (hasResult.profile(result != null)) {
      if (previousStream.profile(previous instanceof ArrayList<?>)) {
        if (resultStream.profile(result instanceof ArrayList<?>)) {
          castExact(previous, ArrayList.class).addAll(castExact(result, ArrayList.class));
        } else {
          castExact(previous, ArrayList.class).add(result);
        }
      } else if (resultStream.profile(result instanceof ArrayList<?>)) {
        castExact(result, ArrayList.class).addFirst(previous);
        frame.setObjectStatic(getResultSlot(), result);
      } else {
        ArrayList<Object> values = new ArrayList<>();
        values.add(previous);
        values.add(result);
        frame.setObjectStatic(getResultSlot(), values);
      }
    }
  }

  public static ResultAggregatingNode create(ValueNode resultNode) {
    return new ResultAggregatingNode(resultNode);
  }
}
