package tailspin.language.nodes.transform;

import static com.oracle.truffle.api.CompilerDirectives.castExact;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.CountingConditionProfile;
import tailspin.language.nodes.iterate.EndOfStreamException;
import tailspin.language.runtime.stream.ListStream;

public abstract class AppendResultNode extends Node {
  final CountingConditionProfile resultNull = CountingConditionProfile.create();
  final CountingConditionProfile previousStream = CountingConditionProfile.create();
  final CountingConditionProfile previousNullAndResultStream = CountingConditionProfile.create();
  final CountingConditionProfile previousNotNull = CountingConditionProfile.create();

  @SuppressWarnings("unchecked")
  public abstract void execute(VirtualFrame frame, int resultSlot, Object result);

  @Specialization
  void doAppendResult(VirtualFrame frame, int resultSlot, Object result,
      @Cached(inline = true) MergeResultNode mergeResultNode) {
    if (resultNull.profile(result == null)) {
      return;
    }
    Object previous = frame.getObjectStatic(resultSlot);
    if (previousStream.profile(previous instanceof ListStream)) {
      ListStream merged = mergeResultNode.execute(this, castExact(previous, ListStream.class), result);
      frame.setObjectStatic(resultSlot, merged);
    } else if(previousNullAndResultStream.profile(previous == null && result instanceof ListStream)) {
      frame.setObjectStatic(resultSlot, result);
    } else {
      ListStream stream = new ListStream();
      if (previousNotNull.profile(previous != null)) {
        stream.append(previous);
      }
      ListStream merged = mergeResultNode.execute(this, stream, result);
      if (merged.size() == 1) {
        frame.setObjectStatic(resultSlot, merged.next());
      } else {
        frame.setObjectStatic(resultSlot, merged);
      }
    }
  }

  public static AppendResultNode create() {
    return AppendResultNodeGen.create();
  }

  /* The way we use this class, previous is always a ListStream */
  @GenerateInline
  public static abstract class MergeResultNode extends Node {
    public abstract ListStream execute(Node node, ListStream previous, Object result);

    @Specialization(guards = "result == null")
    ListStream doResultNull(ListStream previous, @SuppressWarnings("unused") Object result) {
      return previous;
    }

    @Specialization
    @SuppressWarnings("unchecked")
    ListStream doMerge(ListStream previous, ListStream result) {
      previous.merge(result);
      return previous;
    }

    @Specialization(guards = "interop.isIterator(iterator)", limit = "3")
    @SuppressWarnings("unused")
    ListStream doIterator(ListStream previous, Object iterator,
        @CachedLibrary("iterator") InteropLibrary interop) {
      try {
        while (interop.hasIteratorNextElement(iterator)) {
          previous.append(interop.getIteratorNextElement(iterator));
        }
        return previous;
      } catch (UnsupportedMessageException e) {
        throw new AssertionError(e);
      } catch (StopIterationException e) {
        throw new EndOfStreamException();
      }
    }

    @Specialization(guards = "result != null")
    @SuppressWarnings("unchecked")
    ListStream doAppend(ListStream previous, Object result) {
      previous.append(result);
      return previous;
    }
  }
}
