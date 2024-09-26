package tailspin.language.nodes.iterate;

import static tailspin.language.runtime.Templates.LENS_CONTEXT_SLOT;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.profiles.CountingConditionProfile;
import java.util.ArrayList;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.ChainStageNode.SetChainCvNode;
import tailspin.language.nodes.iterate.RangeIterationNodeGen.InitializeRangeIteratorNodeGen;
import tailspin.language.nodes.iterate.RangeIterationNodeGen.RangeIteratorNodeGen;
import tailspin.language.nodes.matchers.GreaterThanMatcherNode;
import tailspin.language.nodes.matchers.LessThanMatcherNode;
import tailspin.language.nodes.matchers.NumericTypeMatcherNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.processor.MessageNode;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;

@NodeChild(value = "start", type = ValueNode.class)
@NodeChild(value = "end", type = ValueNode.class)
@NodeChild(value = "increment", type = ValueNode.class)
public abstract class RangeIteration extends TransformNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private RangeRepeatingNode repeatingNode;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private InitializeRangeIteratorNode initializeNode;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private LoopNode loop;

  final int startSlot;
  final int endSlot;
  final int incrementSlot;

  private final boolean inclusiveEnd;

  @CompilationFinal
  private boolean isLensRange = false;

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  MatcherNode isGt0Node = GreaterThanMatcherNode.create(false, NumericTypeMatcherNode.create(),
      IntegerLiteral.create(0L));

  protected RangeIteration(int startSlot, int endSlot, int incrementSlot, boolean inclusiveStart, boolean inclusiveEnd) {
    this.startSlot = startSlot;
    this.endSlot = endSlot;
    this.incrementSlot = incrementSlot;
    this.inclusiveEnd = inclusiveEnd;
    initializeNode = InitializeRangeIteratorNode.create(startSlot, endSlot, incrementSlot, inclusiveStart);
  }

  public void setIsLensRange() {
    isLensRange = true;
  }

  public void setStage(int rangeCvSlot, TransformNode stage) {
    repeatingNode = new RangeRepeatingNode(rangeCvSlot, stage, startSlot, endSlot, incrementSlot, inclusiveEnd);
    loop = Truffle.getRuntime().createLoopNode(repeatingNode);
  }

  @Override
  public void setResultSlot(int resultSlot) {
    super.setResultSlot(resultSlot);
    repeatingNode.setResultSlot(resultSlot);
  }

  public static RangeIteration create(int rangeCvSlot, TransformNode stage, int startSlot, ValueNode start,
      boolean inclusiveStart, int endSlot, ValueNode end, boolean inclusiveEnd, int incrementSlot, ValueNode increment) {
    RangeIteration created = RangeIterationNodeGen.create(startSlot, endSlot, incrementSlot, inclusiveStart, inclusiveEnd, start, end, increment);
    created.setStage(rangeCvSlot, stage);
    return created;
  }

  public static RangeIteration create(int startSlot, ValueNode start, boolean inclusiveStart, int endSlot, ValueNode end, boolean inclusiveEnd, int incrementSlot, ValueNode increment) {
    return RangeIterationNodeGen.create(startSlot, endSlot, incrementSlot, inclusiveStart, inclusiveEnd, start, end, increment);
  }

  @Specialization
  public void doIterate(VirtualFrame frame, Object start, Object end, Object increment,
      @Cached(value = "createGetFirst()", neverDefault = true) MessageNode getFirst,
      @Cached(value = "createGetLast()", neverDefault = true) MessageNode getLast) {
    if (start == null) {
      if (isGt0Node.executeMatcherGeneric(frame, increment)) start = getFirst.executeMessage(frame.getObjectStatic(LENS_CONTEXT_SLOT));
      else start = getLast.executeMessage(frame.getObjectStatic(LENS_CONTEXT_SLOT));
    }
    if (end == null) {
      if (isGt0Node.executeMatcherGeneric(frame, increment)) end = getLast.executeMessage(frame.getObjectStatic(LENS_CONTEXT_SLOT));
      else end = getFirst.executeMessage(frame.getObjectStatic(LENS_CONTEXT_SLOT));
    }
    initializeNode.executeInitialize(frame, start, end, increment);
    if (isLensRange) {
      frame.setObjectStatic(getResultSlot(), new ArrayList<>());
    }
    loop.execute(frame);
  }

  MessageNode createGetFirst() {
    return MessageNode.create("first", null);
  }

  MessageNode createGetLast() {
    return MessageNode.create("last", null);
  }

  static class RangeRepeatingNode extends Node implements RepeatingNode {
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private RangeIteratorNode iterator;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private SetChainCvNode setCurrentValue;

    @SuppressWarnings("FieldMayBeFinal")
    @Child
    TransformNode stage;

    RangeRepeatingNode(int rangeCvSlot, TransformNode stage, int currentSlot, int endSlot, int incrementSlot,
        boolean inclusiveEnd) {
      iterator = RangeIteratorNode.create(currentSlot, endSlot, incrementSlot, inclusiveEnd);
      setCurrentValue = SetChainCvNode.create(rangeCvSlot);
      this.stage = stage;
    }

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
      try {
        setCurrentValue.execute(frame, iterator.execute(frame));
      } catch (EndOfStreamException e) {
        return false;
      }
      stage.executeTransform(frame);
      return true;
    }

    void setResultSlot(int resultSlot) {
      stage.setResultSlot(resultSlot);
    }
  }

  static abstract class InitializeRangeIteratorNode extends Node {
    final int startSlot;
    final int endSlot;
    final int incrementSlot;

    final boolean inclusiveStart;

    InitializeRangeIteratorNode(int startSlot, int endSlot, int incrementSlot, boolean inclusiveStart) {
      this.startSlot = startSlot;
      this.endSlot = endSlot;
      this.incrementSlot = incrementSlot;
      this.inclusiveStart = inclusiveStart;
    }

    public abstract void executeInitialize(VirtualFrame frame, Object start, Object end, Object increment);

    @Specialization(guards = "inclusiveStart")
    void doInitializeInclusive(VirtualFrame frame, Object start, Object end, Object increment,
        @Cached(inline = true) @Shared("start") WriteLocalValueNode writeStart,
        @Cached(inline = true) @Shared("end") WriteLocalValueNode writeEnd,
        @Cached(inline = true) @Shared("increment") WriteLocalValueNode writeIncrement) {
      writeStart.executeGeneric(frame, this, startSlot, start);
      writeEnd.executeGeneric(frame, this, endSlot, end);
      writeIncrement.executeGeneric(frame, this, incrementSlot, increment);
    }

    @Specialization
    void doInitialize(VirtualFrame frame, Object start, Object end, Object increment,
        @Cached(inline = true) @Shared("start") WriteLocalValueNode writeStart,
        @Cached(inline = true) @Shared("end") WriteLocalValueNode writeEnd,
        @Cached(inline = true) @Shared("increment") WriteLocalValueNode writeIncrement,
        @Cached(value = "createAddNode()", neverDefault = true) AddNode addNode) {
      writeStart.executeGeneric(frame, this, startSlot, addNode.executeAdd(frame, start, increment));
      writeEnd.executeGeneric(frame, this, endSlot, end);
      writeIncrement.executeGeneric(frame, this, incrementSlot, increment);
    }

    AddNode createAddNode() {
      return AddNode.create(null, null, false);
    }

    public static InitializeRangeIteratorNode create(int startSlot, int endSlot, int incrementSlot, boolean inclusiveStart) {
      return InitializeRangeIteratorNodeGen.create(startSlot, endSlot, incrementSlot, inclusiveStart);
    }
  }

  static abstract class RangeIteratorNode extends Node {
    @SuppressWarnings("FieldMayBeFinal")
    @Child @Executed
    ValueNode currentNode;
    @SuppressWarnings("FieldMayBeFinal")
    @Child @Executed
    ValueNode endNode;
    @SuppressWarnings("FieldMayBeFinal")
    @Child @Executed
    ValueNode incrementNode;
    @SuppressWarnings("FieldMayBeFinal")
    @Child @Executed(with = "incrementNode")
    MatcherNode isGt0Node = GreaterThanMatcherNode.create(false, NumericTypeMatcherNode.create(),
        IntegerLiteral.create(0L));

    final boolean inclusiveEnd;
    final int currentSlot;

    RangeIteratorNode(int currentSlot, int endSlot, int incrementSlot, boolean inclusiveEnd) {
      this.currentNode = ReadContextValueNode.create(-1, currentSlot);
      this.endNode = ReadContextValueNode.create(-1, endSlot);
      this.incrementNode = ReadContextValueNode.create(-1, incrementSlot);
      this.inclusiveEnd = inclusiveEnd;
      this.currentSlot = currentSlot;
    }

    public abstract Object execute(VirtualFrame frame);

    final CountingConditionProfile doneProfile = CountingConditionProfile.create();

    @Specialization(guards = "isGt0")
    Object doIncreasing(VirtualFrame frame, Object current, Object end, Object increment, boolean isGt0,
        @Cached(value = "createUpperBoundExceeded()", neverDefault = true) GreaterThanMatcherNode upperBoundExceeded,
        @Cached(inline = true) @Shared("current") WriteLocalValueNode writeCurrent,
        @Cached(value = "createAddNode()", neverDefault = true) @Shared AddNode addNode) {
      if (doneProfile.profile(upperBoundExceeded.executeComparison(frame, current, end))) {
        throw new EndOfStreamException();
      }
      writeCurrent.executeGeneric(frame, this, currentSlot, addNode.executeAdd(frame, current, increment));
      return current;
    }

    GreaterThanMatcherNode createUpperBoundExceeded() {
      return GreaterThanMatcherNode.create(!inclusiveEnd, NumericTypeMatcherNode.create(), null);
    }

    AddNode createAddNode() {
      return AddNode.create(null, null, false);
    }

    @Specialization
    Object doDecreasing(VirtualFrame frame, Object current, Object end, Object increment, boolean isGt0,
        @Cached(value = "createLowerBoundExceeded()", neverDefault = true) LessThanMatcherNode lowerBoundExceeded,
        @Cached(inline = true) @Shared("current") WriteLocalValueNode writeCurrent,
        @Cached(value = "createAddNode()", neverDefault = true) @Shared AddNode addNode) {
      if (doneProfile.profile(lowerBoundExceeded.executeComparison(frame, current, end))) {
        throw new EndOfStreamException();
      }
      writeCurrent.executeGeneric(frame, this, currentSlot, addNode.executeAdd(frame, current, increment));
      return current;
    }

    LessThanMatcherNode createLowerBoundExceeded() {
      return LessThanMatcherNode.create(!inclusiveEnd, NumericTypeMatcherNode.create(), null);
    }

    public static RangeIteratorNode create(int currentSlot, int endSlot, int incrementSlot, boolean inclusiveEnd) {
      return RangeIteratorNodeGen.create(currentSlot, endSlot, incrementSlot, inclusiveEnd);
    }
  }
}
