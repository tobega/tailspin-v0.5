package tailspin.language.nodes.transform;

import static com.oracle.truffle.api.CompilerDirectives.castExact;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.CountingConditionProfile;
import java.util.ArrayList;

public class AppendResultNode extends Node {
  final CountingConditionProfile previousNull = CountingConditionProfile.create();
  final CountingConditionProfile hasResult = CountingConditionProfile.create();
  final CountingConditionProfile previousStream = CountingConditionProfile.create();
  final CountingConditionProfile resultStream = CountingConditionProfile.create();

  @SuppressWarnings("unchecked")
  public void execute(VirtualFrame frame, int resultSlot, Object result) {
    Object previous = frame.getObjectStatic(resultSlot);
    if (previousNull.profile(previous == null)) {
      frame.setObjectStatic(resultSlot, result);
    } else if (hasResult.profile(result != null)) {
      if (previousStream.profile(previous instanceof ArrayList<?>)) {
        if (resultStream.profile(result instanceof ArrayList<?>)) {
          castExact(previous, ArrayList.class).addAll(castExact(result, ArrayList.class));
        } else {
          castExact(previous, ArrayList.class).add(result);
        }
      } else if (resultStream.profile(result instanceof ArrayList<?>)) {
        castExact(result, ArrayList.class).addFirst(previous);
        frame.setObjectStatic(resultSlot, result);
      } else {
        ArrayList<Object> values = new ArrayList<>();
        values.add(previous);
        values.add(result);
        frame.setObjectStatic(resultSlot, values);
      }
    }
  }

  public static AppendResultNode create() {
    return new AppendResultNode();
  }
}
