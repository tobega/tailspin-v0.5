package tailspin;

import java.math.BigInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.openjdk.jmh.annotations.Benchmark;
import tailspin.language.runtime.Rational;
import tailspin.language.runtime.SmallRational;
import tailspin.language.runtime.TailspinArray;

/**
 * A benchmark that uses the naive implementation of the Fibonacci function.
 * The code calculates the 20th Fibonacci number.
 */
@SuppressWarnings("unused")
public class HilbertBenchmark extends TruffleBenchmark {
  private static final String invertHilbert = """
      invertHilbert templates
        n is $;
        -- 1. Initialize Augmented Matrix [H | I]
        @ set [ 1..$n -> templates
          rowIdx is $;
          [ 1..$n -> 1 / ($rowIdx + $ - 1), -- Hilbert Part
            1..$n -> templates
              -- Identity Part
              when <|=$rowIdx> do 1 / 1 !
              otherwise 0 / 1 !
            end
          ] !
        end];
        -- 2. Gaussian Elimination (Forward and Backward)
        1..$n -> auxiliary templates
          i is $;
          pivot is $@invertHilbert($i; $i);
          -- Normalize pivot row: row(i) = row(i) / pivot
          @invertHilbert($i) set $@invertHilbert($i; ..; -> $ / $pivot);
          -- Eliminate other rows
          1..$n -> auxiliary templates
            when <~|=$i> do
              k is $;
              factor is $@invertHilbert($k;$i);
              -- row(k) = row(k) - (factor * row(i))
              @invertHilbert($k) set $@invertHilbert($k; .. as j ; -> $ - ($factor * $@invertHilbert($i; $j)));
          end -> !VOID
        end -> !VOID
        -- 3. Extract the right-hand side (Inverse)
        $@(..; $n + 1..) !
      end invertHilbert
      """;

  interface Sum extends Consumer<Object> {
    long result();
  }

  @Benchmark
  public void small_rational_tailspin() {
    int n = 14;
    doInvertHilbert(n, r -> ((SmallRational) r).denominator() == 1, new Sum() {
      long sum = 0;
      @Override
      public void accept(Object o) {
        sum += ((SmallRational) o).numerator();
      }

      @Override
      public long result() {
        return sum;
      }
    });
  }

  @Benchmark
  public void rational_tailspin() {
    int n = 20;
    doInvertHilbert(n, r -> ((Rational) r).denominator().equals(BigInteger.ONE), new Sum() {
      BigInteger sum = BigInteger.ZERO;
      @Override
      public void accept(Object o) {
        sum = sum.add(((Rational) o).numerator());
      }

      @Override
      public long result() {
        return sum.longValueExact();
      }
    });
  }

  private void doInvertHilbert(int n, Predicate<Object> test, Sum sum) {
    TailspinArray inverse = truffleContext.eval("tt", invertHilbert + n + " -> invertHilbert !").as(TailspinArray.class);
    if (inverse.getArraySize() != n) throw new AssertionError("Wrong number of rows " + inverse.getArraySize());
    for (int i = 0; i < n; i++) {
      TailspinArray row = (TailspinArray) inverse.getNative(i, false);
      if (row.getArraySize() != n) {
        throw new AssertionError("wrong length for row " + i + ". Inverse is " + inverse);
      }
      for (int j = 0; j < n; j++) {
        Object element = row.getNative(j, false);
        if (!test.test(element)) {
          throw new AssertionError("Failed at " + i + "," + j + ":\n" + row);
        }
        sum.accept(element);
      }
    }
    if (sum.result() != n * n) {
      throw new AssertionError("Expected sum " + n*n + " was " + sum.result());
    }
  }
}