package tailspin;

import java.util.ArrayList;
import java.util.List;
import org.openjdk.jmh.annotations.Benchmark;
import tailspin.language.runtime.TailspinArray;

/**
 * A benchmark that uses the naive implementation of the Fibonacci function.
 * The code calculates the 20th Fibonacci number.
 */
@SuppressWarnings("unused")
public class PascalBenchmark extends TruffleBenchmark {
  private static final String tailspinProgram = """
      next-row templates
        in is $;
        @ set 0;
        [1 -> #, $@] !
      
        when <|..$in::length> do
          $@ + $in($) !
          @ set $in($);
          $ + 1 -> # !
      end next-row

      triangle source
        [[1] -> #] !
      
        when <|[](..50)> do
          $!
          $ -> next-row -> # !
        otherwise $ !
      end triangle
      
      $triangle !
      """;

  @Benchmark
  public void triangle_tailspin() {
    TailspinArray triangle = truffleContext.eval("tt", tailspinProgram).as(TailspinArray.class);
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
}