package tailspin;

import org.openjdk.jmh.annotations.Benchmark;

/**
 * A benchmark that uses the naive implementation of the Fibonacci function.
 * The code calculates the 20th Fibonacci number.
 */
@SuppressWarnings("unused")
public class FibonacciBenchmark extends TruffleBenchmark {
  private static final String tailspinProgram = """
      20 -> templates
        when <|=0> do 0 !
        when <|=1> do 1 !
        otherwise ($ - 1 -> #) + ($ - 2 -> #) !
      end !
      """;

  @Benchmark
  public int recursive_tailspin() {
    int value = truffleContext.eval("tt", tailspinProgram).asInt();
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
}