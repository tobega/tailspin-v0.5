package tailspin.language.nodes.array;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.nodes.iterate.GetNextRangeValueNode;
import tailspin.language.runtime.stream.ListStream;
import tailspin.language.runtime.stream.RangeStream;

@GenerateInline(value = false)
public abstract class FlattenResultNode extends Node {
  public abstract void executeFlatten(VirtualFrame frame, ListStream collector, Object value);

  @Specialization(guards = "noValue == null")
  public void doNull(VirtualFrame frame,ListStream collector, Object noValue) {
    // Do nothing
  }

  @Specialization
  public void doStream(VirtualFrame frame,ListStream collector, ListStream stream) {
    while (stream.hasNext()) {
      executeFlatten(frame, collector, stream.next());
    }
  }

  @Specialization
  public void doRange(VirtualFrame frame, ListStream collector, RangeStream range,
      @Cached(inline = true, neverDefault = true) GetNextRangeValueNode getNextRangeValueNode) {
    for (Object value = getNextRangeValueNode.execute(frame, this, range);
        value != null;
        value = getNextRangeValueNode.execute(frame, this, range)) {
      collector.append(value);
    }
  }

  @Fallback
  public void doObject(VirtualFrame frame, ListStream collector, Object value) {
    collector.append(value);
  }

  public static FlattenResultNode create() {
    return FlattenResultNodeGen.create();
  }
}
