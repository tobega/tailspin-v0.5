package tailspin;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.List;
import java.util.function.Supplier;
import org.openjdk.jmh.annotations.Benchmark;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.EqualityMatcherNodeGen;
import tailspin.language.nodes.math.SubtractNodeGen;
import tailspin.language.nodes.transform.EmitNode;
import tailspin.language.nodes.transform.MatchStatementNode;
import tailspin.language.nodes.transform.MatchTemplateNode;
import tailspin.language.nodes.transform.SendToTemplatesNode;
import tailspin.language.nodes.transform.TemplatesRootNode;
import tailspin.language.nodes.transform.ValueTransformNode;
import tailspin.language.nodes.value.LocalDefinitionNodeGen;
import tailspin.language.nodes.value.LocalReferenceNodeGen;
import tailspin.language.nodes.value.SendToTemplatesValueNode;
import tailspin.language.nodes.value.TransformValueNode;
import tailspin.language.nodes.value.ValueTemplatesRootNode;
import tailspin.language.nodes.value.math.AddNodeGen;
import tailspin.language.nodes.value.math.IntegerLiteral;
import tailspin.language.runtime.ResultIterator;
import tailspin.language.runtime.Templates;

/**
 * A benchmark that uses the naive implementation of the Fibonacci function.
 * The code calculates the 20th Fibonacci number.
 */
public class FibonacciBenchmark extends TruffleBenchmark {
  private static final Supplier<Integer> tailspinFibonacci = createTailspinCall();
  private static final Supplier<Integer> tailspinValueFibonacci = createTailspinValueCall();

  @Benchmark
  public int recursive_eval_tailspin() {
    int value = tailspinFibonacci.get();
    if (value != 6765) throw new AssertionError("value " + value);
    return value;
  }

  @Benchmark
  public int recursive_eval_tailspin_value() {
    int value = tailspinValueFibonacci.get();
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
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    Templates templates = new Templates();
    MatcherNode eq0 = EqualityMatcherNodeGen.create(
        LocalReferenceNodeGen.create(cvSlot), new IntegerLiteral(0));
    StatementNode whenEq0 = new EmitNode(new ValueTransformNode(new IntegerLiteral(0)), resultSlot);

    MatcherNode eq1 = EqualityMatcherNodeGen.create(
        LocalReferenceNodeGen.create(cvSlot), new IntegerLiteral(1));
    StatementNode whenEq1 = new EmitNode(new ValueTransformNode(new IntegerLiteral(1)), resultSlot);

    MatcherNode alwaysTrue = new AlwaysTrueMatcherNode();
    ValueNode prevInd = SubtractNodeGen.create(LocalReferenceNodeGen.create(cvSlot), new IntegerLiteral(1));
    TransformNode sendPrev = new SendToTemplatesNode(prevInd, templates);
    ValueNode prevPrevInd = SubtractNodeGen.create(LocalReferenceNodeGen.create(cvSlot), new IntegerLiteral(2));
    TransformNode sendPrevPrev = new SendToTemplatesNode(prevPrevInd, templates);
    ValueNode sum = AddNodeGen.create(new TransformValueNode(sendPrev), new TransformValueNode(sendPrevPrev));
    StatementNode otherwise = new EmitNode(new ValueTransformNode(sum), resultSlot);

    MatchStatementNode matchStatement = new MatchStatementNode(List.of(
        new MatchTemplateNode(eq0, whenEq0),
        new MatchTemplateNode(eq1, whenEq1),
        new MatchTemplateNode(alwaysTrue, otherwise)
    ));

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), cvSlot, matchStatement, resultSlot);
    templates.setCallTarget(callTarget);
    return () -> {
      ResultIterator results = (ResultIterator) callTarget.call(new Object[]{20L});
      return ((Long) results.getIteratorNextElement()).intValue();
    };
  }

  private static Supplier<Integer> createTailspinValueCall() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);

    Templates templates = new Templates();
    MatcherNode eq0 = EqualityMatcherNodeGen.create(
        LocalReferenceNodeGen.create(cvSlot), new IntegerLiteral(0));
    StatementNode whenEq0 = LocalDefinitionNodeGen.create(new IntegerLiteral(0), resultSlot);

    MatcherNode eq1 = EqualityMatcherNodeGen.create(
        LocalReferenceNodeGen.create(cvSlot), new IntegerLiteral(1));
    StatementNode whenEq1 = LocalDefinitionNodeGen.create(new IntegerLiteral(1), resultSlot);

    MatcherNode alwaysTrue = new AlwaysTrueMatcherNode();
    ValueNode prevInd = SubtractNodeGen.create(LocalReferenceNodeGen.create(cvSlot), new IntegerLiteral(1));
    ValueNode sendPrev = new SendToTemplatesValueNode(prevInd, templates);
    ValueNode prevPrevInd = SubtractNodeGen.create(LocalReferenceNodeGen.create(cvSlot), new IntegerLiteral(2));
    ValueNode sendPrevPrev = new SendToTemplatesValueNode(prevPrevInd, templates);
    ValueNode sum = AddNodeGen.create(sendPrev, sendPrevPrev);
    StatementNode otherwise = LocalDefinitionNodeGen.create(sum, resultSlot);

    MatchStatementNode matchStatement = new MatchStatementNode(List.of(
        new MatchTemplateNode(eq0, whenEq0),
        new MatchTemplateNode(eq1, whenEq1),
        new MatchTemplateNode(alwaysTrue, otherwise)
    ));

    CallTarget callTarget = ValueTemplatesRootNode.create(fdb.build(), cvSlot, matchStatement, resultSlot);
    templates.setCallTarget(callTarget);
    return () -> {
      Long result = (Long) callTarget.call(new Object[]{20L});
      return result.intValue();
    };
  }
}