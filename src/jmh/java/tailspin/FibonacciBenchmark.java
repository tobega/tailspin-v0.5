package tailspin;

import org.openjdk.jmh.annotations.Benchmark;
import tailspin.language.runtime.Measure;

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

  private static final String tailspinBigNumberProgram = """
      200000000000000000000 -> templates
        when <|=0> do 0 !
        when <|=10000000000000000000> do 1 !
        otherwise ($ - 10000000000000000000 -> #) + ($ - 20000000000000000000 -> #) !
      end !
      """;

  private static final String tailspinRationalProgram = """
      20/3 -> templates
        when <|=0> do 0 !
        when <|=1/3> do 1 !
        otherwise ($ - 1/3 -> #) + ($ - 2/3 -> #) !
      end !
      """;

  private static final String tailspinSciNumProgram = """
      20.0 -> templates
        when <|=0> do 0 !
        when <|=1> do 1 !
        otherwise ($ - 1 -> #) + ($ - 2 -> #) !
      end !
      """;

  private static final String tailspinMeasureProgram = """
      20"n" -> templates
        when <|=0"n"> do 0"1" !
        when <|=1"n"> do 1"1" !
        otherwise ($ - 1"n" -> #) + ($ - 2"n" -> #) !
      end !
      """;

  @Benchmark
  public void recursive_tailspin_raw_long() {
    int value = truffleContext.eval("tt", tailspinProgram).asInt();
    if (value != 6765) throw new AssertionError("value " + value);
  }

  @Benchmark
  public void recursive_tailspin_bignumber() {
    int value = truffleContext.eval("tt", tailspinBigNumberProgram).asInt();
    if (value != 6765) throw new AssertionError("value " + value);
  }

  @Benchmark
  public void recursive_tailspin_rational() {
    int value = truffleContext.eval("tt", tailspinRationalProgram).asInt();
    if (value != 6765) throw new AssertionError("value " + value);
  }

  @Benchmark
  public void recursive_tailspin_scinum() {
    int value = truffleContext.eval("tt", tailspinSciNumProgram).asInt();
    if (value != 6765) throw new AssertionError("value " + value);
  }

  @Benchmark
  public void recursive_tailspin_measure_long() {
    Measure value = truffleContext.eval("tt", tailspinMeasureProgram).as(Measure.class);
    if ((long) value.value() != 6765) throw new AssertionError("value " + value);
  }

  @Benchmark
  public void recursive_java() {
    int value = fibonacciRecursive(20);
    if (value != 6765) throw new AssertionError("value " + value);
  }

  public static int fibonacciRecursive(int n) {
    return n < 2
        ? n
        : fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
  }
}