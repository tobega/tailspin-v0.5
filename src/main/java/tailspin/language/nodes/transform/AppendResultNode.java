package tailspin.language.nodes.transform;

import static com.oracle.truffle.api.CompilerDirectives.castExact;

import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
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

  @GenerateInline
  public static abstract class MergeResultNode extends Node {
    public abstract Object execute(Node node, Object previous, Object result);

    @Specialization(guards = "previous == null")
    Object doPreviousNull(@SuppressWarnings("unused") Object previous, Object result) {
      return result;
    }

    @Specialization(guards = "result == null")
    Object doResultNull(Object previous, @SuppressWarnings("unused") Object result) {
      return previous;
    }

    @Specialization
    @SuppressWarnings("unchecked")
    ArrayList<?> doMerge(ArrayList<?> previous, ArrayList<?> result) {
      castExact(previous, ArrayList.class).addAll(castExact(result, ArrayList.class));
      return previous;
    }

    @Specialization(guards = "result != null")
    @SuppressWarnings("unchecked")
    ArrayList<?> doAppend(ArrayList<?> previous, Object result) {
      castExact(previous, ArrayList.class).add(result);
      return previous;
    }

    @Specialization(guards = "previous != null")
    @SuppressWarnings("unchecked")
    ArrayList<?> doPrepend(Object previous, ArrayList<?> result) {
      castExact(result, ArrayList.class).addFirst(previous);
      return result;
    }

    @Specialization
    ArrayList<?> doNewList(Object previous, Object result) {
      ArrayList<Object> list = new ArrayList<>();
      list.add(previous);
      list.add(result);
      return list;
    }
  }
}
