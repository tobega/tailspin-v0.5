package tailspin;

import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.RESULT_SLOT;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import org.openjdk.jmh.annotations.Benchmark;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.array.ArrayLiteral;
import tailspin.language.nodes.array.ArrayReadNode;
import tailspin.language.nodes.matchers.LessThanMatcherNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.numeric.RangeLiteral;
import tailspin.language.nodes.numeric.SubtractNode;
import tailspin.language.nodes.processor.MessageNode;
import tailspin.language.nodes.transform.BlockNode;
import tailspin.language.nodes.transform.ChainNode;
import tailspin.language.nodes.transform.EmitNode;
import tailspin.language.nodes.transform.SendToTemplatesNode;
import tailspin.language.nodes.transform.TemplatesRootNode;
import tailspin.language.nodes.value.LocalReferenceNode;
import tailspin.language.nodes.value.StaticReferenceNode;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.Templates;

/**
 * A benchmark that uses a simple implementation of bubblesort.
 */
@SuppressWarnings("unused")
public class BubblesortBenchmark extends TruffleBenchmark {
  private static final Supplier<TailspinArray> tailspinSort = createTailspinCall();

  @Benchmark
  public void sort_tailspin() {
    TailspinArray sorted = tailspinSort.get();
    // TODO: verify result
    if (sorted.getArraySize() != 200) throw new AssertionError("Out of order " + sorted.getArraySize());
  }

  @Benchmark
  public void sort_java() {
    long[] input = LongStream.iterate(100L, i -> i > 0L, i -> i - 1L).flatMap(i -> LongStream.of(i, 100L - i)).toArray();
    long[] output = sortedCopy(input);
    for (int i = 1; i < output.length; i++)
      if (output[i - 1] > output[i])
        throw new AssertionError("Not sorted " + Arrays.toString(output));
  }

  public static long[] sortedCopy(long[] in) {
    long[] out = Arrays.copyOf(in, in.length);
    for (int i = out.length; i > 1; i--) {
      for (int j = 1; j < i; j++) {
        if (out[j - 1] > out[j]) {
          long temp = out[j - 1];
          out[j - 1] = out[j];
          out[j] = temp;
        }
      }
    }
    return out;
  }

  private static Supplier<TailspinArray> createTailspinCall() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int nestedChainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int nestedChainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int nestedChainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    // templates sortedCopy
    Templates sortedCopy = new Templates();
    defineSortedCopy(sortedCopy);
    // end sortedCopy

    // [100..1:-1
    RangeLiteral backwards = RangeLiteral.create(IntegerLiteral.create(100L), IntegerLiteral.create(1L), IntegerLiteral.create(-1L));

    // -> \($! 100 - $!\)
    Templates flatMap = new Templates();
    BlockNode flatMapBlock = BlockNode.create(List.of(
        EmitNode.create(LocalReferenceNode.create(cvSlot), resultSlot),
        EmitNode.create(SubtractNode.create(IntegerLiteral.create(100L), LocalReferenceNode.create(cvSlot)), resultSlot)
    ));
    flatMap.setCallTarget(TemplatesRootNode.create(fdb.build(), flatMapBlock));

     ChainNode arrayContents = ChainNode.create(nestedChainValuesSlot, nestedChainCvSlot, nestedChainResultSlot, List.of(
        backwards,
        SendToTemplatesNode.create(nestedChainCvSlot, flatMap, 0)
    ));
    // ]
    ArrayLiteral input = ArrayLiteral.create(List.of(arrayContents));

    // -> sortedCopy
    ChainNode task = ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot, List.of(
        input,
        SendToTemplatesNode.create(chainCvSlot, sortedCopy, 0)
    ));

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), EmitNode.create(task, resultSlot));
    return () -> (TailspinArray) callTarget.call(null, null);
  }

  private static void defineSortedCopy(Templates sortedCopy) {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    Templates sortedCopyMatchers = new Templates();
    defineSortedCopyMatchers(sortedCopyMatchers);
    // @: $;
    // $::length..2:-1
    RangeLiteral allI = RangeLiteral.create(
        MessageNode.create("length", LocalReferenceNode.create(CV_SLOT)),
        IntegerLiteral.create(2),
        IntegerLiteral.create(-1));
    // -> 2..$
    RangeLiteral allJ = RangeLiteral.create(
        IntegerLiteral.create(2),
        LocalReferenceNode.create(chainCvSlot),
        IntegerLiteral.create(1)
    );
    // -> #
    SendToTemplatesNode toMatchers = SendToTemplatesNode.create(chainCvSlot, sortedCopyMatchers, 0);
    ChainNode iterate = ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot, List.of(
        allI,
        allJ,
        toMatchers
    ));
    // $@ !
    // TODO: emit actual state
    EmitNode emitState = EmitNode.create(LocalReferenceNode.create(CV_SLOT), RESULT_SLOT);

    BlockNode sortedCopyBlock = BlockNode.create(List.of(
        // TODO: setState
        EmitNode.create(iterate, RESULT_SLOT), // TODO: this should be a void block
        emitState
    ));
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), sortedCopyBlock);
    sortedCopy.setCallTarget(callTarget);
  }

  private static void defineSortedCopyMatchers(Templates sortedCopyMatchers) {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    // when <?($@($) <..~$@($ - 1)>)> do
    // TODO: How do we access state? Not 9999
    MatcherNode whenDisordered = LessThanMatcherNode.create(false,
        ArrayReadNode.create(StaticReferenceNode.create(9999), LocalReferenceNode.create(CV_SLOT)),
        ArrayReadNode.create(StaticReferenceNode.create(9999),
            SubtractNode.create(LocalReferenceNode.create(CV_SLOT), IntegerLiteral.create(1)))
    );
    //   def temp: $@($);
    //   @($): $@($ - 1);
    //   @($ - 1): $temp;
    // TODO: replace empty block with actual functionality
    BlockNode fakeMatchStatement = BlockNode.create(List.of());
    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), fakeMatchStatement);
    sortedCopyMatchers.setCallTarget(callTarget);
  }
}