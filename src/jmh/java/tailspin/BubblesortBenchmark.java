package tailspin;

import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.EMIT_SLOT;
import static tailspin.language.runtime.Templates.STATE_SLOT;
import static tailspin.language.runtime.Templates.createBasicFdb;
import static tailspin.language.runtime.Templates.createScopeFdb;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import org.openjdk.jmh.annotations.Benchmark;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ProgramRootNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.array.ArrayLiteral;
import tailspin.language.nodes.array.ArrayReadNode;
import tailspin.language.nodes.array.ArrayWriteNode;
import tailspin.language.nodes.iterate.ChainNode;
import tailspin.language.nodes.iterate.RangeIteration;
import tailspin.language.nodes.iterate.ResultAggregatingNode;
import tailspin.language.nodes.matchers.ConditionNode;
import tailspin.language.nodes.matchers.GreaterThanMatcherNode;
import tailspin.language.nodes.matchers.LessThanMatcherNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.numeric.SubtractNode;
import tailspin.language.nodes.processor.MessageNode;
import tailspin.language.nodes.state.FreezeNode;
import tailspin.language.nodes.transform.BlockNode;
import tailspin.language.nodes.transform.DefineTemplatesNode;
import tailspin.language.nodes.transform.EmitNode;
import tailspin.language.nodes.transform.MatchBlockNode;
import tailspin.language.nodes.transform.MatchTemplateNode;
import tailspin.language.nodes.transform.SendToTemplatesNode;
import tailspin.language.nodes.transform.CallDefinedTemplatesNode;
import tailspin.language.nodes.transform.SinkNode;
import tailspin.language.nodes.transform.TemplatesRootNode;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.nodes.value.WriteContextValueNode;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.Templates;

/**
 * A benchmark that uses a simple implementation of bubblesort.
 */
@SuppressWarnings("unused")
public class BubblesortBenchmark extends TruffleBenchmark {

  private static final Supplier<TailspinArray> tailspinSortIterate = createTailspinCall(
      defineSortedCopy());
  private static final Supplier<TailspinArray> tailspinSortRecurse = createTailspinCall(
      defineBubblesort());

  @Benchmark
  public void sort_tailspin_iterate() {
    TailspinArray sorted = tailspinSortIterate.get();
    if (sorted.getArraySize() != 100) {
      throw new AssertionError("Too short array " + sorted.getArraySize());
    }
    for (int i = 1; i < sorted.getArraySize(); i++) {
      if ((long) sorted.getNative(i - 1) > (long) sorted.getNative(i)) {
        throw new AssertionError("Out of order " + sorted.getArraySize());
      }
    }
  }

  @Benchmark
  public void sort_tailspin_recurse() {
    TailspinArray sorted = tailspinSortRecurse.get();
    if (sorted.getArraySize() != 100) {
      throw new AssertionError("Too short array " + sorted.getArraySize());
    }
    for (int i = 1; i < sorted.getArraySize(); i++) {
      if ((long) sorted.getNative(i - 1) > (long) sorted.getNative(i)) {
        throw new AssertionError("Out of order " + sorted.getArraySize());
      }
    }
  }

  @Benchmark
  public void sort_java() {
    List<Long> input = LongStream.iterate(50L, i -> i > 0L, i -> i - 1L)
        .flatMap(i -> LongStream.of(i, 50L - i)).boxed()
        .toList();
    List<Long> output = sortedCopy(input);
    if (output.size() != 100) {
      throw new AssertionError("Too short array " + output.size());
    }
    for (int i = 1; i < output.size(); i++) {
      if (output.get(i - 1) > output.get(i)) {
        throw new AssertionError("Not sorted " + output);
      }
    }
  }

  public static List<Long> sortedCopy(List<Long> in) {
    List<Long> out = new ArrayList<>(in);
    for (int i = out.size(); i > 1; i--) {
      for (int j = 1; j < i; j++) {
        if (out.get(j - 1) > out.get(j)) {
          long temp = out.get(j - 1);
          out.set(j - 1, out.get(j));
          out.set(j, temp);
        }
      }
    }
    return out;
  }

  private static Supplier<TailspinArray> createTailspinCall(Templates sortedCopy) {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainIsFirstSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int rangeCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int buildArraySlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    FrameDescriptor.Builder scopeFdb = createScopeFdb();

    Templates flatMap = new Templates();
    flatMap.setDefinitionLevel(1);
    // [50..1:-1
    RangeIteration backwards = RangeIteration.create(rangeCvSlot,
        SendToTemplatesNode.create(ReadContextValueNode.create(-1, rangeCvSlot), 1, flatMap),
        IntegerLiteral.create(50L), IntegerLiteral.create(1L), IntegerLiteral.create(-1L));

    // -> \($! 100 - $!\)
    BlockNode flatMapBlock = BlockNode.create(List.of(
        EmitNode.create(ResultAggregatingNode.create(ReadContextValueNode.create(-1, CV_SLOT))),
        EmitNode.create(ResultAggregatingNode.create(
            SubtractNode.create(IntegerLiteral.create(100L), ReadContextValueNode.create(-1, CV_SLOT))))
    ));
    flatMap.setCallTarget(TemplatesRootNode.create(createBasicFdb().build(), null, flatMapBlock));

    // ]
    ArrayLiteral input = ArrayLiteral.create(buildArraySlot, List.of(backwards));

    // -> sortedCopy
    ChainNode task = ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot, List.of(
        ResultAggregatingNode.create(input),
        SendToTemplatesNode.create(ReadContextValueNode.create(-1, chainCvSlot), 1, sortedCopy)
    ));
    task.setResultSlot(EMIT_SLOT);

    CallTarget callTarget = ProgramRootNode.create(null, fdb.build(), scopeFdb.build(),
        BlockNode.create(List.of(
          EmitNode.create(task))
        ));
    return () -> (TailspinArray) callTarget.call();
  }

  private static Templates defineSortedCopy() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int rangeISlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int sinkSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    FrameDescriptor.Builder scopeFdb = createScopeFdb();

    Templates sortedCopyMatchers = new Templates();
    sortedCopyMatchers.setDefinitionLevel(1);
    defineSortedCopyMatchers(sortedCopyMatchers);
    // @: $;
    WriteContextValueNode setState = WriteContextValueNode.create(0, STATE_SLOT, ReadContextValueNode.create(-1, CV_SLOT));
    // $::length..2:-1
    // -> 2..$
    // -> !#
    Templates allJTemplates = new Templates();
    allJTemplates.setDefinitionLevel(2);
    FrameDescriptor.Builder jfdb = Templates.createBasicFdb();
    int rangeJSlot = jfdb.addSlot(FrameSlotKind.Illegal, null, null);
    // Seems like the wrong call level here
    SendToTemplatesNode toMatchers = SendToTemplatesNode.create(ReadContextValueNode.create(-1, rangeJSlot), 1, sortedCopyMatchers);
    allJTemplates.setNeedsScope();
    RangeIteration allJ = RangeIteration.create(
        rangeJSlot,
        toMatchers,
        IntegerLiteral.create(2),
        ReadContextValueNode.create(-1, CV_SLOT),
        IntegerLiteral.create(1)
    );
    SendToTemplatesNode doAllJ = SendToTemplatesNode.create(ReadContextValueNode.create(-1, rangeISlot), 2, allJTemplates);
    allJTemplates.setCallTarget(TemplatesRootNode.create(jfdb.build(), null, EmitNode.create(allJ)));
    RangeIteration allI = RangeIteration.create(
        rangeISlot,
        doAllJ,
        MessageNode.create("length", ReadContextValueNode.create(-1, CV_SLOT)),
        IntegerLiteral.create(2),
        IntegerLiteral.create(-1));
    allI.setResultSlot(sinkSlot);
    // $@ !
    EmitNode emitState = EmitNode.create(
        ResultAggregatingNode.create(FreezeNode.create(ReadContextValueNode.create(0, STATE_SLOT))));

    BlockNode sortedCopyBlock = BlockNode.create(List.of(
        setState,
        SinkNode.create(allI),
        emitState
    ));
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), scopeFdb.build(), sortedCopyBlock);
    Templates sortedCopy = new Templates();
    sortedCopy.setDefinitionLevel(1);
    sortedCopy.setCallTarget(callTarget);
    return sortedCopy;
  }

  private static void defineSortedCopyMatchers(Templates sortedCopyMatchers) {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int tempSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    // when <?($@($) <..~$@($ - 1)>)> do
    MatcherNode isDisordered = ConditionNode.create(
        ArrayReadNode.create(ReadContextValueNode.create(0, STATE_SLOT), ReadContextValueNode.create(-1, CV_SLOT)),
        LessThanMatcherNode.create(false,
        ArrayReadNode.create(ReadContextValueNode.create(0, STATE_SLOT),
            SubtractNode.create(ReadContextValueNode.create(-1, CV_SLOT), IntegerLiteral.create(1)))
    ));
    sortedCopyMatchers.setNeedsScope();
    //   def temp: $@($);
    WriteContextValueNode defTemp = WriteContextValueNode.create(-1, tempSlot, ArrayReadNode.create(
        ReadContextValueNode.create(0, STATE_SLOT), ReadContextValueNode.create(-1, CV_SLOT)));
    //   @($): $@($ - 1);
    WriteContextValueNode setStateCurrent = WriteContextValueNode.create(0, STATE_SLOT, ArrayWriteNode.create(
        ReadContextValueNode.create(0, STATE_SLOT),
        ReadContextValueNode.create(-1, CV_SLOT),
        ArrayReadNode.create(ReadContextValueNode.create(0, STATE_SLOT),
            SubtractNode.create(ReadContextValueNode.create(-1, CV_SLOT), IntegerLiteral.create(1))
        )
    ));
    //   @($ - 1): $temp;
    WriteContextValueNode setStatePrev = WriteContextValueNode.create(0, STATE_SLOT, ArrayWriteNode.create(
        ReadContextValueNode.create(0, STATE_SLOT),
        SubtractNode.create(ReadContextValueNode.create(-1, CV_SLOT), IntegerLiteral.create(1)),
        ReadContextValueNode.create(-1, tempSlot)
    ));
    BlockNode whenDisordered = BlockNode.create(List.of(
        defTemp,
        setStateCurrent,
        setStatePrev
    ));
    MatchBlockNode matchStatement = MatchBlockNode.create(List.of(
        MatchTemplateNode.create(isDisordered, whenDisordered)));
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), null, matchStatement);
    sortedCopyMatchers.setCallTarget(callTarget);
  }

  private static Templates defineBubblesort() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainIsFirstSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    FrameDescriptor.Builder scopeFdb = createScopeFdb();
    int bubbleSlot = scopeFdb.addSlot(FrameSlotKind.Illegal, null, null);

    //    templates bubblesort
    Templates bubblesort = new Templates();
    bubblesort.setDefinitionLevel(1);
    Templates bubble = new Templates();
    bubble.setDefinitionLevel(2);
    defineBubble(bubble);
    Templates matchers = new Templates();
    matchers.setDefinitionLevel(1);
    //
    //    @: $;
    WriteContextValueNode setState = WriteContextValueNode.create(0, STATE_SLOT, ReadContextValueNode.create(-1, CV_SLOT));
    //    $::length -> !#
    ChainNode lengthToMatchers = ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot,
        List.of(
            ResultAggregatingNode.create(
                MessageNode.create("length", ReadContextValueNode.create(-1, CV_SLOT))),
            SendToTemplatesNode.create(ReadContextValueNode.create(-1, chainCvSlot), 1, matchers)
        ));
    //    $@ !
    EmitNode emit = EmitNode.create(
        ResultAggregatingNode.create(FreezeNode.create(ReadContextValueNode.create(0, STATE_SLOT))));
    defineBubblesortMatchers(matchers, bubbleSlot);
    //    end bubblesort
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), scopeFdb.build(), BlockNode.create(List.of(
        DefineTemplatesNode.create(bubble, bubbleSlot),
        setState,
        SinkNode.create(lengthToMatchers),
        emit
    )));
    bubblesort.setCallTarget(callTarget);

    return bubblesort;
  }

  private static void defineBubblesortMatchers(Templates matchers, int bubbleSlot) {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainIsFirstSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    //        when <2..> do
    MatcherNode isGteq2 = GreaterThanMatcherNode.create(true, IntegerLiteral.create(2));
    //      $ -> bubble -> !#
    StatementNode whenGteq2 = SinkNode.create(
        ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot, List.of(
            ResultAggregatingNode.create(ReadContextValueNode.create(-1, CV_SLOT)),
            CallDefinedTemplatesNode.create(ReadContextValueNode.create(-1, chainCvSlot), ReadContextValueNode.create(0, bubbleSlot)),
            SendToTemplatesNode.create(ReadContextValueNode.create(-1, chainCvSlot), 1, matchers)
        )));
    matchers.setNeedsScope();
    MatchBlockNode matchStatement = MatchBlockNode.create(List.of(
        MatchTemplateNode.create(isGteq2, whenGteq2)));
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), null, matchStatement);
    matchers.setCallTarget(callTarget);
  }

  private static void defineBubble(Templates bubble) {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    FrameDescriptor.Builder scopeFdb = createScopeFdb();

    Templates matchers = new Templates();
    matchers.setDefinitionLevel(2);
    //    templates bubble
    //    @: 1;
    WriteContextValueNode setState = WriteContextValueNode.create(0, STATE_SLOT, IntegerLiteral.create(1));
    //    1..$-1 -> !#
    SinkNode doTemplates = SinkNode.create(RangeIteration.create(
        chainCvSlot,
        SendToTemplatesNode.create(ReadContextValueNode.create(-1, chainCvSlot), 2, matchers),
        IntegerLiteral.create(1),
        SubtractNode.create(ReadContextValueNode.create(-1, CV_SLOT), IntegerLiteral.create(1)),
        IntegerLiteral.create(1))
    );
    //    $@ !
    EmitNode emit = EmitNode.create(
        ResultAggregatingNode.create(FreezeNode.create(ReadContextValueNode.create(0, STATE_SLOT))));
    defineBubbleMatchers(matchers);
    //    end bubble
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), scopeFdb.build(), BlockNode.create(List.of(
        setState,
        doTemplates,
        emit
    )));
    bubble.setCallTarget(callTarget);
  }

  private static void defineBubbleMatchers(Templates matchers) {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int tempSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    //        when <?($@bubblesort($+1) <..~$@bubblesort($)>)> do
    MatcherNode isDisordered = ConditionNode.create(
        ArrayReadNode.create(ReadContextValueNode.create(1, STATE_SLOT),
            AddNode.create(ReadContextValueNode.create(-1, CV_SLOT), IntegerLiteral.create(1))),
        LessThanMatcherNode.create(false,
        ArrayReadNode.create(ReadContextValueNode.create(1, STATE_SLOT),
            ReadContextValueNode.create(-1, CV_SLOT))
    ));
    matchers.setNeedsScope();
    //      @: $;
    WriteContextValueNode markSwap = WriteContextValueNode.create(0, STATE_SLOT, ReadContextValueNode.create(-1, CV_SLOT));
    //    def temp: $@bubblesort($@);
    WriteContextValueNode defTemp = WriteContextValueNode.create(-1, tempSlot, ArrayReadNode.create(
        ReadContextValueNode.create(1, STATE_SLOT), ReadContextValueNode.create(0, STATE_SLOT)));
    //    @bubblesort($@): $@bubblesort($@+1);
    WriteContextValueNode setStateCurrent = WriteContextValueNode.create(1, STATE_SLOT, ArrayWriteNode.create(
        ReadContextValueNode.create(1, STATE_SLOT),
        ReadContextValueNode.create(0, STATE_SLOT),
        ArrayReadNode.create(ReadContextValueNode.create(1, STATE_SLOT),
            AddNode.create(ReadContextValueNode.create(0, STATE_SLOT), IntegerLiteral.create(1))
        )
    ));
    //    @bubblesort($@+1): $temp;
    WriteContextValueNode setStateNext = WriteContextValueNode.create(1, STATE_SLOT, ArrayWriteNode.create(
        ReadContextValueNode.create(1, STATE_SLOT),
        AddNode.create(ReadContextValueNode.create(0, STATE_SLOT), IntegerLiteral.create(1)),
        ReadContextValueNode.create(-1, tempSlot)
    ));
    BlockNode whenDisordered = BlockNode.create(List.of(
        markSwap,
        defTemp,
        setStateCurrent,
        setStateNext
    ));
    MatchBlockNode matchStatement = MatchBlockNode.create(List.of(
        MatchTemplateNode.create(isDisordered, whenDisordered)));
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), null, matchStatement);
    matchers.setCallTarget(callTarget);
  }
}