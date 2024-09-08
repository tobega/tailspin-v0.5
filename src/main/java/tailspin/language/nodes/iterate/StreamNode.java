package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.api.strings.TruffleStringIterator;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.transform.AppendResultNode;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.TailspinStrings;

@NodeChild(value = "value", type = ValueNode.class)
public abstract class StreamNode extends TransformNode {

  private static final int ZERO_WIDTH_JOINER = 8205;

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private AppendResultNode appendResultNode = AppendResultNode.create();

  @Specialization
  void doArray(VirtualFrame frame, TailspinArray ta) {
    appendResultNode.execute(frame, getResultSlot(), ta.stream());
  }

  @Specialization
  void doString(VirtualFrame frame, TruffleString ts,
      @Cached TruffleString.CreateCodePointIteratorNode createCodePointIteratorNode,
      @Cached TruffleStringIterator.NextNode nextNode,
      @Cached TruffleStringBuilder.AppendCodePointNode appendCodePointNode,
      @Cached TruffleStringBuilder.ToStringNode toStringNode) {
    TruffleStringIterator iterator = TailspinStrings.getCodePointIterator(ts,
        createCodePointIteratorNode);
    TruffleStringBuilder part = TailspinStrings.newBuilder();
    boolean pending = false;
    while (iterator.hasNext()) {
      int cp = nextNode.execute(iterator);
      if (cp == ZERO_WIDTH_JOINER) {
        pending = true;
        appendCodePointNode.execute(part, cp);
        continue;
      }
      int type = Character.getType(cp);
      if (!pending && !part.isEmpty() && type != Character.COMBINING_SPACING_MARK
          && type != Character.ENCLOSING_MARK
          && type != Character.NON_SPACING_MARK) {
        appendResultNode.execute(frame, getResultSlot(), toStringNode.execute(part));
        part = TailspinStrings.newBuilder();
      }
      appendCodePointNode.execute(part, cp);
      pending = false;
    }
    if (!part.isEmpty()) {
      appendResultNode.execute(frame, getResultSlot(), toStringNode.execute(part));
    }
  }

  public static StreamNode create(ValueNode value) {
    return StreamNodeGen.create(value);
  }
}
