package tailspin.language.nodes.iterate;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;
import static tailspin.language.runtime.Templates.LENS_CONTEXT_SLOT;

import com.oracle.truffle.api.dsl.Bind;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.GetNextRangeValueNode.GtZeroNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.processor.MessageNode;
import tailspin.language.runtime.Measure;
import tailspin.language.runtime.stream.RangeStream;

@NodeChild(value = "start", type = ValueNode.class)
@NodeChild(value = "end", type = ValueNode.class)
@NodeChild(value = "increment", type = ValueNode.class)
public abstract class CreateRangeNode extends ValueNode {

  private final boolean inclusiveStart;

  private final boolean inclusiveEnd;

  CreateRangeNode(boolean inclusiveStart, boolean inclusiveEnd,
      SourceSection sourceSection) {
    super(sourceSection);
    this.inclusiveStart = inclusiveStart;
    this.inclusiveEnd = inclusiveEnd;
  }

  public static CreateRangeNode create(ValueNode start, boolean inclusiveStart, ValueNode end, boolean inclusiveEnd, ValueNode increment, SourceSection sourceSection) {
    return CreateRangeNodeGen.create(inclusiveStart, inclusiveEnd, sourceSection, start, end, increment);
  }

  public abstract RangeStream executeCreateRange(VirtualFrame frame, Object start, Object end, Object increment);

  @Specialization(guards = "increment == null")
  public RangeStream doIterateMeasureDefault(VirtualFrame frame, Measure start, Measure end, Object increment) {
    return executeCreateRange(frame, start, end, new Measure(1L, start.unit()));
  }

  @Specialization(guards = "increment == null")
  public RangeStream doIterateNumberDefault(VirtualFrame frame, Object start, Object end, Object increment) {
    return executeCreateRange(frame, start, end, 1L);
  }

  @Specialization(guards = {
      "isPositiveIncrement == isPositiveIncrement(node, gtZeroNode, increment)",
      "start == null",
      "increment != null"
  }, limit = "2")
  public RangeStream doIterateInferStart(
      VirtualFrame frame,
      Object start,
      Object end,
      Object increment,
      @Bind("this") Node node,
      @Cached(inline = true) @Shared GtZeroNode gtZeroNode,
      @Cached("isPositiveIncrement(node, gtZeroNode, increment)") boolean isPositiveIncrement,
      @Cached(value = "createGetFirst()", neverDefault = true) @Shared MessageNode getFirst,
      @Cached(value = "createGetLast()", neverDefault = true) @Shared MessageNode getLast) {
    Object context = frame.getObjectStatic(LENS_CONTEXT_SLOT);
    if (isPositiveIncrement) start = getFirst.executeMessage(context);
    else start = getLast.executeMessage(context);
    return executeCreateRange(frame, start, end, increment);
  }

  @Specialization(guards = {
      "isPositiveIncrement == isPositiveIncrement(node, gtZeroNode, increment)",
      "end == null",
      "increment != null"
  }, limit = "2")
  public RangeStream doIterateInferEnd(VirtualFrame frame, Object start, Object end, Object increment,
      @Bind("this") Node node,
      @Cached(inline = true) @Shared GtZeroNode gtZeroNode,
      @Cached("isPositiveIncrement(node, gtZeroNode, increment)") boolean isPositiveIncrement,
      @Cached(value = "createGetFirst()", neverDefault = true) @Shared MessageNode getFirst,
      @Cached(value = "createGetLast()", neverDefault = true) @Shared MessageNode getLast) {
    Object context = frame.getObjectStatic(LENS_CONTEXT_SLOT);
    if (isPositiveIncrement) end = getLast.executeMessage(context);
    else end = getFirst.executeMessage(context);
    return executeCreateRange(frame, start, end, increment);
  }

  @Specialization(guards = {"start != null", "end != null", "increment != null"})
  public RangeStream doIterate(VirtualFrame frame, Object start, Object end, Object increment,
      @Cached(value = "createAddNode()", neverDefault = true) AddNode addNode) {
    if (!inclusiveStart) {
      start = addNode.executeAdd(start, increment);
    }
    return new RangeStream(start, end, increment, inclusiveEnd);
  }

  MessageNode createGetFirst() {
    return MessageNode.create("first", null, INTERNAL_CODE_SOURCE);
  }

  MessageNode createGetLast() {
    return MessageNode.create("last", null, INTERNAL_CODE_SOURCE);
  }

  AddNode createAddNode() {
    return AddNode.create(null, null, false, INTERNAL_CODE_SOURCE);
  }

  static boolean isPositiveIncrement(Node node, GtZeroNode gtZeroNode, Object increment) {
    return gtZeroNode.executeTest(node, increment);
  }
}
