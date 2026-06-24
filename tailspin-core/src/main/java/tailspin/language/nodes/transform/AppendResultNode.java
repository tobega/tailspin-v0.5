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
import tailspin.language.nodes.array.FlattenResultNode;
import tailspin.language.nodes.iterate.EndOfStreamException;
import tailspin.language.runtime.stream.ListStream;
import tailspin.language.runtime.stream.RangeStream;

public abstract class AppendResultNode extends Node {
  final CountingConditionProfile resultNull = CountingConditionProfile.create();
  final CountingConditionProfile previousStream = CountingConditionProfile.create();
  final CountingConditionProfile previousNullAndResultStream = CountingConditionProfile.create();
  final CountingConditionProfile previousNotNull = CountingConditionProfile.create();

  @SuppressWarnings("unchecked")
  public abstract void execute(VirtualFrame frame, int resultSlot, Object result);

  @Specialization
  void doAppendResult(VirtualFrame frame, int resultSlot, Object result,
      @Cached AppendListStreamNode appendListStreamNode) {
    if (resultNull.profile(result == null)) {
      return;
    }
    Object previous = frame.getObjectStatic(resultSlot);
    if (previousStream.profile(previous instanceof ListStream)) {
      ListStream stream = castExact(previous, ListStream.class);
      appendListStreamNode.execute(frame, stream, result);
    } else if(previousNullAndResultStream.profile(previous == null && (result instanceof ListStream || result instanceof RangeStream))) {
      frame.setObjectStatic(resultSlot, result);
    } else {
      ListStream stream = new ListStream();
      if (previousNotNull.profile(previous != null)) {
        stream.append(previous);
      }
      appendListStreamNode.execute(frame, stream, result);
      if (stream.size() == 1) {
        frame.setObjectStatic(resultSlot, stream.next());
      } else {
        frame.setObjectStatic(resultSlot, stream );
      }
    }
  }

  public static AppendResultNode create() {
    return AppendResultNodeGen.create();
  }

  public static abstract class AppendListStreamNode extends Node {
    public abstract void execute(VirtualFrame frame, ListStream previous, Object result);

    @Specialization(guards = "!previous.flat")
    void doRegular(VirtualFrame frame, ListStream previous, Object result,
        @Cached(inline = true) MergeResultNode mergeResultNode) {
      mergeResultNode.execute(frame, this, previous, result);
    }

    @Specialization(guards = "previous.flat")
    void doFlat(VirtualFrame frame, ListStream previous, Object result,
        @Cached(neverDefault = true) FlattenResultNode flattenResultNode) {
      flattenResultNode.executeFlatten(frame, previous, result);
    }
  }

  /* The way we use this class, previous is always a ListStream */
  @GenerateInline
  public static abstract class MergeResultNode extends Node {
    public abstract void execute(VirtualFrame frame, Node node, ListStream previous, Object result);

    @Specialization(guards = "result == null")
    static void doResultNull(VirtualFrame frame, Node node, ListStream previous, @SuppressWarnings("unused") Object result) {
      // do nothing
    }

    @Specialization
    @SuppressWarnings("unchecked")
    static void doMerge(VirtualFrame frame, Node node, ListStream previous, ListStream result) {
      previous.merge(result);
    }

    @Specialization
    @SuppressWarnings("unchecked")
    static void doMergeRange(VirtualFrame frame, Node node, ListStream previous, RangeStream range) {
      previous.append(range);
    }

    @Specialization(guards = "interop.isIterator(iterator)", limit = "3")
    @SuppressWarnings("unused")
    static void doIterator(VirtualFrame frame, Node node, ListStream previous, Object iterator,
        @CachedLibrary("iterator") InteropLibrary interop) {
      try {
        while (interop.hasIteratorNextElement(iterator)) {
          previous.append(interop.getIteratorNextElement(iterator));
        }
      } catch (UnsupportedMessageException e) {
        throw new AssertionError(e);
      } catch (StopIterationException e) {
        throw new EndOfStreamException();
      }
    }

    @Specialization(guards = "result != null")
    @SuppressWarnings("unchecked")
    static void doAppend(VirtualFrame frame, Node node, ListStream previous, Object result) {
      previous.append(result);
    }
  }
}
