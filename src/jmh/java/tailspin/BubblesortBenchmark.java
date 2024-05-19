package tailspin;

import static tailspin.language.runtime.Templates.CV_SLOT;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import org.openjdk.jmh.annotations.Benchmark;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.array.ArrayLiteral;
import tailspin.language.nodes.array.ArrayReadNode;
import tailspin.language.nodes.array.ArrayWriteNode;
import tailspin.language.nodes.iterate.ChainNode;
import tailspin.language.nodes.iterate.RangeIteration;
import tailspin.language.nodes.matchers.GreaterThanMatcherNode;
import tailspin.language.nodes.matchers.LessThanMatcherNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.numeric.SubtractNode;
import tailspin.language.nodes.processor.MessageNode;
import tailspin.language.nodes.state.FreezeNode;
import tailspin.language.nodes.state.GetStateNode;
import tailspin.language.nodes.state.SetStateNode;
import tailspin.language.nodes.transform.BlockNode;
import tailspin.language.nodes.transform.EmitNode;
import tailspin.language.nodes.transform.MatchStatementNode;
import tailspin.language.nodes.transform.MatchTemplateNode;
import tailspin.language.nodes.transform.SendToTemplatesNode;
import tailspin.language.nodes.transform.SinkNode;
import tailspin.language.nodes.transform.TemplatesRootNode;
import tailspin.language.nodes.value.LocalDefinitionNode;
import tailspin.language.nodes.value.LocalReferenceNode;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.Templates;

/**
 * A benchmark that uses a simple implementation of bubblesort.
 */
@SuppressWarnings("unused")
public class BubblesortBenchmark extends TruffleBenchmark {
  private static final Supplier<TailspinArray> tailspinSort = createTailspinCall(defineSortedCopy());
  private static final Supplier<TailspinArray> tailspinSort2 = createTailspinCall(defineBubblesort());

  @Benchmark
  public void sort_tailspin() {
    TailspinArray sorted = tailspinSort.get();
    if (sorted.getArraySize() != 100) throw new AssertionError("Too short array " + sorted.getArraySize());
    for (int i = 1; i < sorted.getArraySize(); i++)
      if ((long) sorted.getNative(i - 1) > (long) sorted.getNative(i))
        throw new AssertionError("Out of order " + sorted.getArraySize());
  }

  @Benchmark
  public void sort2_tailspin() {
    TailspinArray sorted = tailspinSort2.get();
    if (sorted.getArraySize() != 100) throw new AssertionError("Too short array " + sorted.getArraySize());
    for (int i = 1; i < sorted.getArraySize(); i++)
      if ((long) sorted.getNative(i - 1) > (long) sorted.getNative(i))
        throw new AssertionError("Out of order " + sorted.getArraySize());
  }

  @Benchmark
  public void sort_java() {
    List<Long> input = LongStream.iterate(50L, i -> i > 0L, i -> i - 1L).flatMap(i -> LongStream.of(i, 50L - i)).boxed()
        .toList();
    List<Long> output = sortedCopy(input);
    if (output.size() != 100) throw new AssertionError("Too short array " + output.size());
    for (int i = 1; i < output.size(); i++)
      if (output.get(i - 1) > output.get(i))
        throw new AssertionError("Not sorted " + output);
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
    int nestedChainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int nestedChainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int nestedChainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    Templates flatMap = new Templates();
    // [50..1:-1
    RangeIteration backwards = RangeIteration.create(nestedChainCvSlot, SendToTemplatesNode.create(nestedChainCvSlot, flatMap, 0), nestedChainResultSlot, IntegerLiteral.create(50L), IntegerLiteral.create(1L), IntegerLiteral.create(-1L));

    // -> \($! 100 - $!\)
    BlockNode flatMapBlock = BlockNode.create(List.of(
        EmitNode.create(LocalReferenceNode.create(CV_SLOT)),
        EmitNode.create(SubtractNode.create(IntegerLiteral.create(100L), LocalReferenceNode.create(CV_SLOT)))
    ));
    flatMap.setCallTarget(TemplatesRootNode.create(fdb.build(), flatMapBlock));

    // ]
    ArrayLiteral input = ArrayLiteral.create(List.of(backwards));

    // -> sortedCopy
    ChainNode task = ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot, List.of(
        input,
        SendToTemplatesNode.create(chainCvSlot, sortedCopy, 0)
    ));

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), EmitNode.create(task));
    return () -> (TailspinArray) callTarget.call(null, null);
  }

  private static Templates defineSortedCopy() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int rangeISlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int resultISlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int rangeJSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int resultJSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int stateSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    Templates sortedCopyMatchers = new Templates();
    defineSortedCopyMatchers(sortedCopyMatchers, stateSlot);
    // @: $;
    SetStateNode setState = SetStateNode.create(LocalReferenceNode.create(CV_SLOT), 0, stateSlot);
    // $::length..2:-1
    // -> 2..$
    // -> !#
    SendToTemplatesNode toMatchers = SendToTemplatesNode.create(rangeJSlot, sortedCopyMatchers, 0);
    RangeIteration allJ = RangeIteration.create(
        rangeJSlot,
        toMatchers,
        resultJSlot,
        IntegerLiteral.create(2),
        LocalReferenceNode.create(rangeISlot),
        IntegerLiteral.create(1)
    );
    RangeIteration allI = RangeIteration.create(
        rangeISlot,
        allJ,
        resultISlot,
        MessageNode.create("length", LocalReferenceNode.create(CV_SLOT)),
        IntegerLiteral.create(2),
        IntegerLiteral.create(-1));
    // $@ !
    EmitNode emitState = EmitNode.create(FreezeNode.create(GetStateNode.create(0, stateSlot)));

    BlockNode sortedCopyBlock = BlockNode.create(List.of(
        setState,
        SinkNode.create(allI),
        emitState
    ));
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), sortedCopyBlock);
    Templates sortedCopy = new Templates();
    sortedCopy.setCallTarget(callTarget);
    return sortedCopy;
  }

  private static void defineSortedCopyMatchers(Templates sortedCopyMatchers, int stateSlot) {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int tempSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    // when <?($@($) <..~$@($ - 1)>)> do
    MatcherNode isDisordered = LessThanMatcherNode.create(false,
        ArrayReadNode.create(GetStateNode.create(1, stateSlot), LocalReferenceNode.create(CV_SLOT)),
        ArrayReadNode.create(GetStateNode.create(1, stateSlot),
            SubtractNode.create(LocalReferenceNode.create(CV_SLOT), IntegerLiteral.create(1)))
    );
    //   def temp: $@($);
    LocalDefinitionNode defTemp = LocalDefinitionNode.create(ArrayReadNode.create(
        GetStateNode.create(1, stateSlot), LocalReferenceNode.create(CV_SLOT)), tempSlot);
    //   @($): $@($ - 1);
    SetStateNode setStateCurrent = SetStateNode.create(ArrayWriteNode.create(
        GetStateNode.create(1, stateSlot),
        LocalReferenceNode.create(CV_SLOT),
        ArrayReadNode.create(GetStateNode.create(1, stateSlot),
            SubtractNode.create(LocalReferenceNode.create(CV_SLOT), IntegerLiteral.create(1))
        )
    ), 1, stateSlot);
    //   @($ - 1): $temp;
    SetStateNode setStatePrev = SetStateNode.create(ArrayWriteNode.create(
        GetStateNode.create(1, stateSlot),
        SubtractNode.create(LocalReferenceNode.create(CV_SLOT), IntegerLiteral.create(1)),
        LocalReferenceNode.create(tempSlot)
    ), 1, stateSlot);
    BlockNode whenDisordered = BlockNode.create(List.of(
        defTemp,
        setStateCurrent,
        setStatePrev
    ));
    MatchStatementNode matchStatement = MatchStatementNode.create(List.of(
        MatchTemplateNode.create(isDisordered, whenDisordered)));
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), matchStatement);
    sortedCopyMatchers.setCallTarget(callTarget);
  }

  private static Templates defineBubblesort() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int stateSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    //    templates bubblesort
    Templates bubblesort = new Templates();
    Templates bubble = new Templates();
    defineBubble(bubble, stateSlot);
    Templates matchers = new Templates();
    //
    //    @: $;
    SetStateNode setState = SetStateNode.create(LocalReferenceNode.create(CV_SLOT), 0, stateSlot);
    //    $::length -> !#
    ChainNode lengthToMatchers = ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot,List.of(
        MessageNode.create("length", LocalReferenceNode.create(CV_SLOT)),
        SendToTemplatesNode.create(chainCvSlot, matchers, 0)
    ));
    //    $@ !
    EmitNode emit = EmitNode.create(FreezeNode.create(GetStateNode.create(0, stateSlot)));
    defineBubblesortMatchers(matchers, bubble);
    //    end bubblesort
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), BlockNode.create(List.of(
        setState,
        SinkNode.create(lengthToMatchers),
        emit
    )));
    bubblesort.setCallTarget(callTarget);

    return bubblesort;
  }

  private static void defineBubblesortMatchers(Templates matchers, Templates bubble) {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    //        when <2..> do
    MatcherNode isGteq2 = GreaterThanMatcherNode.create(true, LocalReferenceNode.create(CV_SLOT), IntegerLiteral.create(2));
    //      $ -> bubble -> !#
    StatementNode whenGteq2 = SinkNode.create(ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot, List.of(
        LocalReferenceNode.create(CV_SLOT),
        SendToTemplatesNode.create(chainCvSlot, bubble, 1),
        SendToTemplatesNode.create(chainCvSlot, matchers, 1)
    )));
    MatchStatementNode matchStatement = MatchStatementNode.create(List.of(
        MatchTemplateNode.create(isGteq2, whenGteq2)));
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), matchStatement);
    matchers.setCallTarget(callTarget);
  }

  private static void defineBubble(Templates bubble, int bubblesortStateSlot) {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int stateSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    Templates matchers = new Templates();
    //    templates bubble
    //    @: 1;
    SetStateNode setState = SetStateNode.create(IntegerLiteral.create(1), 0, stateSlot);
    //    1..$-1 -> !#
    SinkNode doTemplates = SinkNode.create(RangeIteration.create(
                chainCvSlot,
            SendToTemplatesNode.create(chainCvSlot, matchers, 0),
                chainResultSlot,
                IntegerLiteral.create(1),
                SubtractNode.create(LocalReferenceNode.create(CV_SLOT), IntegerLiteral.create(1)),
                IntegerLiteral.create(1))
        );
    //    $@ !
    EmitNode emit = EmitNode.create(FreezeNode.create(GetStateNode.create(0, stateSlot)));
    defineBubbleMatchers(matchers, stateSlot, bubblesortStateSlot);
    //    end bubble
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), BlockNode.create(List.of(
        setState,
        doTemplates,
        emit
    )));
    bubble.setCallTarget(callTarget);
  }

  private static void defineBubbleMatchers(Templates matchers, int stateSlot, int bubblesortStateSlot) {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int tempSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    //        when <?($@bubblesort($+1) <..~$@bubblesort($)>)> do
    MatcherNode isDisordered = LessThanMatcherNode.create(false,
        ArrayReadNode.create(GetStateNode.create(2, bubblesortStateSlot),
            AddNode.create(LocalReferenceNode.create(CV_SLOT), IntegerLiteral.create(1))),
        ArrayReadNode.create(GetStateNode.create(2, bubblesortStateSlot), LocalReferenceNode.create(CV_SLOT))
    );
    //      @: $;
    SetStateNode markSwap = SetStateNode.create(LocalReferenceNode.create(CV_SLOT), 1, stateSlot);
    //    def temp: $@bubblesort($@);
    LocalDefinitionNode defTemp = LocalDefinitionNode.create(ArrayReadNode.create(
        GetStateNode.create(2, bubblesortStateSlot), GetStateNode.create(1, stateSlot)), tempSlot);
    //    @bubblesort($@): $@bubblesort($@+1);
    SetStateNode setStateCurrent = SetStateNode.create(ArrayWriteNode.create(
        GetStateNode.create(2, bubblesortStateSlot),
        GetStateNode.create(1, stateSlot),
        ArrayReadNode.create(GetStateNode.create(2, bubblesortStateSlot),
            AddNode.create(GetStateNode.create(1, stateSlot), IntegerLiteral.create(1))
        )
    ), 2, bubblesortStateSlot);
    //    @bubblesort($@+1): $temp;
    SetStateNode setStateNext = SetStateNode.create(ArrayWriteNode.create(
        GetStateNode.create(2, bubblesortStateSlot),
        AddNode.create(GetStateNode.create(1, stateSlot), IntegerLiteral.create(1)),
        LocalReferenceNode.create(tempSlot)
    ), 2, bubblesortStateSlot);
    BlockNode whenDisordered = BlockNode.create(List.of(
        markSwap,
        defTemp,
        setStateCurrent,
        setStateNext
    ));
    MatchStatementNode matchStatement = MatchStatementNode.create(List.of(
        MatchTemplateNode.create(isDisordered, whenDisordered)));
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), matchStatement);
    matchers.setCallTarget(callTarget);
  }
}