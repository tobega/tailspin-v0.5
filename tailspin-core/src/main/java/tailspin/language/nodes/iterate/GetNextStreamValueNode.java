package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.CountingConditionProfile;
import tailspin.language.nodes.iterate.ChainStageNode.StaticReferenceNode;
import tailspin.language.nodes.iterate.GetNextStreamValueNode.StreamPeekNode;
import tailspin.language.nodes.iterate.GetNextStreamValueNodeGen.StreamPeekNodeGen;
import tailspin.language.runtime.stream.ListStream;
import tailspin.language.runtime.stream.RangeStream;

@SuppressWarnings("unused")
@NodeChild(value = "valueStream", type = StaticReferenceNode.class)
@NodeChild(value = "nextValue", type = StreamPeekNode.class, executeWith = "valueStream")
public abstract class GetNextStreamValueNode extends Node {
  public static abstract class StreamPeekNode extends Node {
    public abstract Object executeStreamPeek(VirtualFrame frame, Object stream);

    final CountingConditionProfile emptyProfile = CountingConditionProfile.create();

    @Specialization
    protected Object doStream(
        VirtualFrame frame,
        ListStream stream) {
      if (emptyProfile.profile(!stream.hasNext())) {
        throw new EndOfStreamException();
      }
      return stream.next();
    }

    @Specialization
    protected Object doRange(
        VirtualFrame frame,
        RangeStream range,
        @Cached(neverDefault = true, inline = true) GetNextRangeValueNode iterator) {
      Object value = iterator.execute(frame, this, range);
      if (value == null) {
        throw new EndOfStreamException();
      }
      return value;
    }

    @Specialization(guards = "interop.isIterator(iterator)", limit = "3")
    protected Object doInteropIterator(
        Object iterator, @CachedLibrary("iterator") InteropLibrary interop) {
      try {
        if (interop.hasIteratorNextElement(iterator)) {
          return interop.getIteratorNextElement(iterator);
        } else {
          throw new EndOfStreamException();
        }
      } catch (UnsupportedMessageException e) {
        throw new AssertionError(e);
      } catch (StopIterationException e) {
        throw new EndOfStreamException();
      }
    }

    public static StreamPeekNode create() {
      return StreamPeekNodeGen.create();
    }
  }

  public abstract Object executeStream(VirtualFrame frame);

  @Specialization
  Object doListRange(VirtualFrame frame, ListStream stream, RangeStream range,
      @Cached(neverDefault = true, inline = true) GetNextRangeValueNode iterator) {
    Object value = iterator.execute(frame, this, range);
    if (value == null) {
      throw new EndOfSubIteratorException();
    }
    stream.backUp();
    return value;
  }

  @Fallback
  Object doGeneric(VirtualFrame frame, Object stream, Object value) {
    return value;
  }

  public static GetNextStreamValueNode create(StaticReferenceNode referenceNode) {
    return GetNextStreamValueNodeGen.create(referenceNode, StreamPeekNode.create());
  }
}
