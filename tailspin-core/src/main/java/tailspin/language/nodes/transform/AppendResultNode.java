package tailspin.language.nodes.transform;

import static com.oracle.truffle.api.CompilerDirectives.castExact;

import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.CountingConditionProfile;
import tailspin.language.runtime.stream.ListStream;

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
      if (previousStream.profile(previous instanceof ListStream)) {
        if (resultStream.profile(result instanceof ListStream)) {
          castExact(previous, ListStream.class).merge(castExact(result, ListStream.class));
        } else {
          castExact(previous, ListStream.class).append(result);
        }
      } else if (resultStream.profile(result instanceof ListStream)) {
        castExact(result, ListStream.class).prepend(previous);
        frame.setObjectStatic(resultSlot, result);
      } else {
        ListStream values = new ListStream();
        values.append(previous);
        values.append(result);
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
    ListStream doMerge(ListStream previous, ListStream result) {
      previous.merge(result);
      return previous;
    }

    @Specialization(guards = "result != null")
    @SuppressWarnings("unchecked")
    ListStream doAppend(ListStream previous, Object result) {
      previous.append(result);
      return previous;
    }

    @Specialization(guards = "previous != null")
    @SuppressWarnings("unchecked")
    ListStream doPrepend(Object previous, ListStream result) {
      result.prepend(previous);
      return result;
    }

    @Specialization
    ListStream doNewList(Object previous, Object result) {
      ListStream list = new ListStream();
      list.append(previous);
      list.append(result);
      return list;
    }
  }
}
