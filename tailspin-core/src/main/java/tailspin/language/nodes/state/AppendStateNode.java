package tailspin.language.nodes.state;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.EndOfStreamException;
import tailspin.language.nodes.iterate.GetNextRangeValueNode;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.stream.ListStream;
import tailspin.language.runtime.stream.RangeStream;

@NodeChild(value = "target", type = ValueNode.class)
@NodeChild(value = "value", type = ValueNode.class)
public abstract class AppendStateNode extends ValueNode {

  public AppendStateNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  public abstract Object executeDirect(VirtualFrame frame, Object target, Object value);

  @Specialization
  protected Object appendArrayMany(VirtualFrame frame, TailspinArray array, ListStream values) {
    TailspinArray result = array.getThawed();
    while (values.hasNext()) {
      executeDirect(frame, result, values.next());
    }
    return result;
  }

  @Specialization
  protected Object appendArrayRange(VirtualFrame frame, TailspinArray array, RangeStream range,
      @Cached(neverDefault = true, inline = true) GetNextRangeValueNode getNextRangeValueNode) {
    TailspinArray result = array.getThawed();
    try {
      while (true) {
        Object value = getNextRangeValueNode.execute(frame, this, range);
        executeDirect(frame, result, value);
      }
    } catch (EndOfStreamException e) {}
    return result;
  }

  @Specialization
  TailspinArray appendArray(VirtualFrame frame, TailspinArray target, Object value) {
    TailspinArray result = target.getThawed();
    result.append(value);
    return result;
  }

  @Specialization
  Object cannotAppend(VirtualFrame frame, Object target, Object ignored) {
    CompilerDirectives.transferToInterpreterAndInvalidate();
    throw new TypeError("Cannot append to " + target, this);
  }

  public static AppendStateNode create(ValueNode target, ValueNode value,
      SourceSection sourceSection) {
    return AppendStateNodeGen.create(sourceSection, target, value);
  }
}
