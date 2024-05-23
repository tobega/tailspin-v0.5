package tailspin;

import static tailspin.language.runtime.Templates.CV_SLOT;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.List;
import java.util.function.Supplier;
import org.openjdk.jmh.annotations.Benchmark;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.EqualityMatcherNodeGen;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.numeric.SubtractNode;
import tailspin.language.nodes.iterate.ChainNode;
import tailspin.language.nodes.transform.EmitNode;
import tailspin.language.nodes.transform.MatchStatementNode;
import tailspin.language.nodes.transform.MatchTemplateNode;
import tailspin.language.nodes.transform.SendToTemplatesNode;
import tailspin.language.nodes.transform.TemplatesRootNode;
import tailspin.language.nodes.value.AssertSingleValueNodeGen;
import tailspin.language.nodes.value.LocalReferenceNode;
import tailspin.language.runtime.Templates;

/**
 * A benchmark that uses the naive implementation of the Fibonacci function.
 * The code calculates the 20th Fibonacci number.
 */
@SuppressWarnings("unused")
public class FibonacciBenchmark extends TruffleBenchmark {
  private static final Supplier<Integer> tailspinFibonacci = createTailspinCall();

  @Benchmark
  public int recursive_eval_tailspin() {
    int value = tailspinFibonacci.get();
    if (value != 6765) throw new AssertionError("value " + value);
    return value;
  }

  @Benchmark
  public int recursive_java() {
    int value = fibonacciRecursive(20);
    if (value != 6765) throw new AssertionError("value " + value);
    return value;
  }

  public static int fibonacciRecursive(int n) {
    return n < 2
        ? n
        : fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
  }

  private static Supplier<Integer> createTailspinCall() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainIsFirstSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    // templates fibonacci
    Templates templates = new Templates();
    // when <=0> do 0 !
    MatcherNode eq0 = EqualityMatcherNodeGen.create(
        LocalReferenceNode.create(CV_SLOT), IntegerLiteral.create(0));
    StatementNode whenEq0 = EmitNode.create(IntegerLiteral.create(0));

    // when <=1> do 1!
    MatcherNode eq1 = EqualityMatcherNodeGen.create(
        LocalReferenceNode.create(CV_SLOT), IntegerLiteral.create(1));
    StatementNode whenEq1 = EmitNode.create(IntegerLiteral.create(1));

    // otherwise ($ - 1 -> #) + ($ - 2 -> #) !
    MatcherNode alwaysTrue = new AlwaysTrueMatcherNode();
    SubtractNode prevInd = SubtractNode.create(LocalReferenceNode.create(CV_SLOT), IntegerLiteral.create(1));
    SendToTemplatesNode sendPrev = SendToTemplatesNode.create(chainCvSlot, templates, -1);
    SubtractNode prevPrevInd = SubtractNode.create(LocalReferenceNode.create(CV_SLOT), IntegerLiteral.create(2));
    SendToTemplatesNode sendPrevPrev = SendToTemplatesNode.create(chainCvSlot, templates, -1);
    ValueNode sum = AddNode.create(
        AssertSingleValueNodeGen.create(ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot, List.of(prevInd, sendPrev), chainIsFirstSlot)),
        AssertSingleValueNodeGen.create(ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot, List.of(prevPrevInd, sendPrevPrev), chainIsFirstSlot)));
    StatementNode otherwise = EmitNode.create(sum);

    MatchStatementNode matchStatement = MatchStatementNode.create(List.of(
        MatchTemplateNode.create(eq0, whenEq0),
        MatchTemplateNode.create(eq1, whenEq1),
        MatchTemplateNode.create(alwaysTrue, otherwise)
    ));
    // end fibonacci

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), matchStatement);
    templates.setCallTarget(callTarget);
    return () -> {
      Long results = (Long) callTarget.call(20L, null);
      return results.intValue();
    };
  }
}