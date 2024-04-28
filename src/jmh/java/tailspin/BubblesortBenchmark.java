package tailspin;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import org.openjdk.jmh.annotations.Benchmark;
import tailspin.language.nodes.array.ArrayLiteral;
import tailspin.language.nodes.literals.IntegerLiteral;
import tailspin.language.nodes.literals.RangeLiteral;
import tailspin.language.nodes.math.SubtractNode;
import tailspin.language.nodes.transform.BlockNode;
import tailspin.language.nodes.transform.ChainNode;
import tailspin.language.nodes.transform.EmitNode;
import tailspin.language.nodes.transform.SendToTemplatesNode;
import tailspin.language.nodes.transform.TemplatesRootNode;
import tailspin.language.nodes.value.LocalReferenceNode;
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
    tailspinSort.get();
    // TODO: verify result
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
    // TODO: actually sort these
    EmitNode result = EmitNode.create(LocalReferenceNode.create(cvSlot), resultSlot);
    sortedCopy.setCallTarget(TemplatesRootNode.create(fdb.build(), cvSlot, result, resultSlot));
    // end sortedCopy

    // [100..1:-1
    RangeLiteral backwards = RangeLiteral.create(IntegerLiteral.create(100L), IntegerLiteral.create(1L), IntegerLiteral.create(-1L));

    // -> \($! 100 - $!\)
    Templates flatMap = new Templates();
    BlockNode flatMapBlock = BlockNode.create(List.of(
        EmitNode.create(LocalReferenceNode.create(cvSlot), resultSlot),
        EmitNode.create(SubtractNode.create(IntegerLiteral.create(100L), LocalReferenceNode.create(cvSlot)), resultSlot)
    ));
    flatMap.setCallTarget(TemplatesRootNode.create(fdb.build(), cvSlot, flatMapBlock, resultSlot));

     ChainNode arrayContents = ChainNode.create(nestedChainValuesSlot, nestedChainCvSlot, nestedChainResultSlot, List.of(
        backwards,
        SendToTemplatesNode.create(nestedChainCvSlot, flatMap)
    ));
    // ]
    ArrayLiteral input = ArrayLiteral.create(List.of(arrayContents));

    // -> sortedCopy
    ChainNode task = ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot, List.of(
        input,
        SendToTemplatesNode.create(chainCvSlot, sortedCopy)
    ));

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), cvSlot, EmitNode.create(task, resultSlot), resultSlot);
    return () -> (TailspinArray) callTarget.call(0);
  }
}