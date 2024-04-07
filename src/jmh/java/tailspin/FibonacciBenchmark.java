package tailspin;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import tailspin.arithmetic.ArithmeticOperation;
import tailspin.arithmetic.ArithmeticOperation.Op;
import tailspin.arithmetic.ArithmeticValue;
import tailspin.arithmetic.IntegerConstant;
import tailspin.control.Block;
import tailspin.control.Reference;
import tailspin.control.ResultIterator;
import tailspin.control.SendToTemplates;
import tailspin.interpreter.BasicScope;
import tailspin.interpreter.Scope;
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
import tailspin.language.nodes.value.LocalReferenceNodeGen;
import tailspin.language.nodes.value.TransformValueNode;
import tailspin.language.nodes.value.math.AddNodeGen;
import tailspin.language.nodes.value.math.IntegerLiteral;
import tailspin.language.runtime.Templates;
import tailspin.matchers.AnyOf;
import tailspin.matchers.Equality;
import tailspin.transform.MatchTemplate;

/**
 * A benchmark that uses the naive implementation of the Fibonacci function.
 * The code calculates the 20th Fibonacci number.
 */
public class FibonacciBenchmark extends TruffleBenchmark {
  private static final String FIBONACCI_JS_FUNCTION = """
      function fib(n) {
          if (n < 2) {
              return n;
          }
          return fib(n - 1) + fib(n - 2);
      }
      """;

  private Value jsFibonacci;

  private Value slFibonacci;

  private static final Supplier<Integer> tailspinFibonacci = createTailspinCall();

  private static final Supplier<Integer> v0Fibonacci = createV0Call();

  protected Context truffleContext;

  @Setup
  public void setup() {
    this.truffleContext = Context.create();
    this.truffleContext.eval("js", FIBONACCI_JS_FUNCTION);
    jsFibonacci = this.truffleContext.getBindings("js").getMember("fib");
    this.truffleContext.eval("sl", FIBONACCI_JS_FUNCTION);
    slFibonacci = this.truffleContext.getBindings("sl").getMember("fib");
  }

  @TearDown
  public void tearDown() {
    this.truffleContext.close();
  }

  @Benchmark
  public int recursive_eval_tailspin() {
    int value = tailspinFibonacci.get();
    if (value != 6765) throw new AssertionError("value " + value);
    return value;
  }

  @Benchmark
  public int recursive_eval_v0_tailspin() {
    int value = v0Fibonacci.get();
    if (value != 6765) throw new AssertionError("value " + value);
    return value;
  }

  @Benchmark
  public int recursive_eval_js() {
    int value = jsFibonacci.execute(20).asInt();
    if (value != 6765) throw new AssertionError("value " + value);
    return value;
  }

  @Benchmark
  public int recursive_eval_sl() {
    int value = slFibonacci.execute(20).asInt();
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
      @SuppressWarnings("unchecked")
      Iterator<Object> results = (Iterator<Object>) callTarget.call(new Object[]{20L});
      return ((Long) results.next()).intValue();
    };
  }

  private static Supplier<Integer> createV0Call() {
    List<MatchTemplate> matchTemplates = List.of(
        new MatchTemplate(new AnyOf(false, null, List.of(new Equality(new IntegerConstant(0, null)))),
            new Block(List.of(new ArithmeticValue(new IntegerConstant(0, null))))
        ),
        new MatchTemplate(new AnyOf(false, null, List.of(new Equality(new IntegerConstant(1, null)))),
            new Block(List.of(new ArithmeticValue(new IntegerConstant(1, null))))
        ),
        new MatchTemplate(new AnyOf(false, null, List.of()),
            new Block(List.of(new ArithmeticOperation(
                tailspin.control.Value.of(new SendToTemplates(new ArithmeticOperation(Reference.it(), Op.Subtract, new IntegerConstant(1, null)))),
                Op.Add,
                tailspin.control.Value.of(new SendToTemplates(new ArithmeticOperation(Reference.it(), Op.Subtract, new IntegerConstant(2, null))))
                ))
            )
        )
    );
    Scope scope = new BasicScope(Path.of("."));
    tailspin.transform.Templates templates = new tailspin.transform.Templates("fibonacci", scope, List.of(), null, matchTemplates);
    scope.defineValue("fibonacci", templates);
    return () -> {
      Object result = ResultIterator.resolveSideEffects(templates.getResults(20L, Map.of()));
      return ((Long) result).intValue();
    };
  }
}