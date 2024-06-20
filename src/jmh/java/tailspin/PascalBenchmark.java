package tailspin;

import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.STATE_SLOT;
import static tailspin.language.runtime.Templates.createBasicFdb;
import static tailspin.language.runtime.Templates.createScopeFdb;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.openjdk.jmh.annotations.Benchmark;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ProgramRootNode;
import tailspin.language.nodes.array.ArrayLiteral;
import tailspin.language.nodes.array.ArrayReadNode;
import tailspin.language.nodes.iterate.ChainNode;
import tailspin.language.nodes.iterate.ResultAggregatingNode;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.LessThanMatcherNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.processor.MessageNode;
import tailspin.language.nodes.transform.BlockNode;
import tailspin.language.nodes.transform.CallDefinedTemplatesNode;
import tailspin.language.nodes.transform.DefineTemplatesNode;
import tailspin.language.nodes.transform.EmitNode;
import tailspin.language.nodes.transform.MatchStatementNode;
import tailspin.language.nodes.transform.MatchTemplateNode;
import tailspin.language.nodes.transform.SendToTemplatesNode;
import tailspin.language.nodes.transform.TemplatesRootNode;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.nodes.value.WriteContextValueNode;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.Templates;

/**
 * A benchmark that uses the naive implementation of the Fibonacci function.
 * The code calculates the 20th Fibonacci number.
 */
@SuppressWarnings("unused")
public class PascalBenchmark extends TruffleBenchmark {
  private static final Supplier<TailspinArray> tailspinPascal = createTailspinCall();

  public static void main(String[] args) {
    System.out.println(tailspinPascal.get());
  }

  @Benchmark
  public void triangle_tailspin() {
    TailspinArray triangle = tailspinPascal.get();
    if (triangle.getArraySize() != 51) throw new AssertionError("Wrong number of rows " + triangle.getArraySize());
    for (int i = 1; i < triangle.getArraySize(); i++) {
      if (((TailspinArray) triangle.getNative(i)).getArraySize() != i + 1) {
        throw new AssertionError("wrong length for row " + i + ". Triangle is " + triangle);
      }
      if ((Long) ((TailspinArray) triangle.getNative(i)).getNative(1) != i) {
        throw new AssertionError("Wrong value " + i);
      }
    }
  }

  @Benchmark
  public static void triangle_java() {
    List<List<Long>> triangle = triangle();
    if (triangle.size() != 51) throw new AssertionError("Wrong number of rows " + triangle.size());
    for (int i = 1; i < triangle.size(); i++) {
      if (triangle.get(i).size() != i + 1) {
        throw new AssertionError("wrong length for row " + i);
      }
      if (triangle.get(i).get(1) != i) {
        throw new AssertionError("Wrong value " + i);
      }
    }
  }

  public static List<Long> nextRow(List<Long> row) {
    List<Long> next = new ArrayList<>();
    long seed = 0;
    for (long value : row) {
      next.add(seed + value);
      seed = value;
    }
    next.add(seed);
    return next;
  }

  public static List<List<Long>> triangle() {
    List<List<Long>> result = new ArrayList<>();
    List<Long> row = List.of(1L);
    while (row.size() <= 50) {
      result.add(row);
      row = nextRow(row);
    }
    result.add(row);
    return result;
  }

  private static Supplier<TailspinArray> createTailspinCall() {
    FrameDescriptor.Builder programScope = createScopeFdb();
    int nextRowSlot = programScope.addSlot(FrameSlotKind.Illegal, null, null);
    int triangleSlot = programScope.addSlot(FrameSlotKind.Illegal, null, null);

    Templates triangle = new Templates();

    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int innerBuildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    FrameDescriptor.Builder scopeFdb = createScopeFdb();

    Templates nextRow = defineNextRow();

    Templates matchers = new Templates();
    //  source triangle
    //    [[1] -> #] !
    EmitNode emitTriangle = EmitNode.create(ResultAggregatingNode.create(ArrayLiteral.create(buildSlot, List.of(
        ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot, List.of(
            ResultAggregatingNode.create(ArrayLiteral.create(innerBuildSlot, List.of(ResultAggregatingNode.create(IntegerLiteral.create(1))))),
            SendToTemplatesNode.create(ReadContextValueNode.create(-1, chainCvSlot), matchers, 0)
        ))
    ))));
    CallTarget triangleCallTarget = TemplatesRootNode.create(fdb.build(), scopeFdb.build(),
        emitTriangle);
    triangle.setCallTarget(triangleCallTarget);

    FrameDescriptor.Builder fdbMatch = Templates.createBasicFdb();
    int matchChainValuesSlot = fdbMatch.addSlot(FrameSlotKind.Static, null, null);
    int matchChainCvSlot = fdbMatch.addSlot(FrameSlotKind.Illegal, null, null);
    int matchChainResultSlot = fdbMatch.addSlot(FrameSlotKind.Static, null, null);
    //    when <[](..50)> do
    MatcherNode whenLengthLe50 = LessThanMatcherNode.create(true,
        MessageNode.create("length", ReadContextValueNode.create(-1, CV_SLOT)),
        IntegerLiteral.create(50));
    //      $!
    EmitNode emitValue1 = EmitNode.create(ResultAggregatingNode.create(ReadContextValueNode.create(-1, CV_SLOT)));
    //      $ -> next-row -> #
    ChainNode recurse = ChainNode.create(matchChainValuesSlot, matchChainCvSlot, matchChainResultSlot, List.of(
        ResultAggregatingNode.create(ReadContextValueNode.create(-1, CV_SLOT)),
        CallDefinedTemplatesNode.create(ReadContextValueNode.create(-1, matchChainCvSlot), ReadContextValueNode.create(1, nextRowSlot)),
        SendToTemplatesNode.create(ReadContextValueNode.create(-1, matchChainCvSlot), matchers, 0)
    ));
    //    otherwise
    MatcherNode otherwise = AlwaysTrueMatcherNode.create();
    //      $!
    EmitNode emitValue2 = EmitNode.create(ResultAggregatingNode.create(ReadContextValueNode.create(-1, CV_SLOT)));
    //   end triangle
    matchers.setCallTarget(TemplatesRootNode.create(fdbMatch.build(), null, MatchStatementNode.create(List.of(
        MatchTemplateNode.create(whenLengthLe50, BlockNode.create(List.of(
            emitValue1,
            EmitNode.create(recurse)
        ))),
        MatchTemplateNode.create(otherwise, emitValue2)
    ))));

    CallTarget program = ProgramRootNode.create(null, createBasicFdb().build(), programScope.build(),
        BlockNode.create(List.of(
            DefineTemplatesNode.create(nextRow, nextRowSlot),
            DefineTemplatesNode.create(triangle, triangleSlot),
            EmitNode.create(CallDefinedTemplatesNode.create(ReadContextValueNode.create(-1, CV_SLOT), ReadContextValueNode.create(0, triangleSlot)))
        )));

    return () -> (TailspinArray) program.call();
  }

  private static Templates defineNextRow() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    FrameDescriptor.Builder scopedb = createScopeFdb();
    int inSlot = scopedb.addSlot(FrameSlotKind.Illegal, null, null);

    Templates matchers = new Templates();
    //  templates next-row
    //    def in: $;
    WriteContextValueNode defIn = WriteContextValueNode.create(0, inSlot, ReadContextValueNode.create(-1, CV_SLOT));
    //    @: 0;
    WriteContextValueNode initState = WriteContextValueNode.create(0, STATE_SLOT, IntegerLiteral.create(0));
    //    [1 -> #, $@] !
    EmitNode emitRow = EmitNode.create(ResultAggregatingNode.create(ArrayLiteral.create(buildSlot, List.of(
        ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot, List.of(
            ResultAggregatingNode.create(IntegerLiteral.create(1)),
            SendToTemplatesNode.create(ReadContextValueNode.create(-1, chainCvSlot), matchers, 0)
            )),
        ResultAggregatingNode.create(ReadContextValueNode.create(0, STATE_SLOT))
        ))));
    Templates nextRow = new Templates();
    nextRow.setCallTarget(TemplatesRootNode.create(fdb.build(), scopedb.build(), BlockNode.create(List.of(
        defIn,
        initState,
        emitRow
    ))));

    FrameDescriptor.Builder fdbMatch = Templates.createBasicFdb();
    int matchChainValuesSlot = fdbMatch.addSlot(FrameSlotKind.Static, null, null);
    int matchChainCvSlot = fdbMatch.addSlot(FrameSlotKind.Illegal, null, null);
    int matchChainResultSlot = fdbMatch.addSlot(FrameSlotKind.Static, null, null);
    //    when <..$in::length> do
    MatcherNode whenLeLength = LessThanMatcherNode.create(true,
        ReadContextValueNode.create(-1, CV_SLOT),
        MessageNode.create("length", ReadContextValueNode.create(0, inSlot)));
    //      $@ + $in($) !
    EmitNode emitValue = EmitNode.create(ResultAggregatingNode.create(AddNode.create(ReadContextValueNode.create(0, STATE_SLOT),
        ArrayReadNode.create(ReadContextValueNode.create(0, inSlot), ReadContextValueNode.create(-1, CV_SLOT)))));
    //      @: $in($);
    WriteContextValueNode updateState = WriteContextValueNode.create(0, STATE_SLOT,
        ArrayReadNode.create(ReadContextValueNode.create(0, inSlot), ReadContextValueNode.create(-1, CV_SLOT))
    );
    //      $ + 1 -> #
    ChainNode recurse = ChainNode.create(matchChainValuesSlot, matchChainCvSlot, matchChainResultSlot, List.of(
        ResultAggregatingNode.create(AddNode.create(ReadContextValueNode.create(-1, CV_SLOT), IntegerLiteral.create(1))),
        SendToTemplatesNode.create(ReadContextValueNode.create(-1, matchChainCvSlot), matchers, 0)
    ));
    //  end next-row
    matchers.setCallTarget(TemplatesRootNode.create(fdbMatch.build(), null, MatchStatementNode.create(List.of(
        MatchTemplateNode.create(
          whenLeLength,
          BlockNode.create(List.of(
              emitValue,
              updateState,
              EmitNode.create(recurse)
          ))
        )
    ))));
    return nextRow;
  }
}