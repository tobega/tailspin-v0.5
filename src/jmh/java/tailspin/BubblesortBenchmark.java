package tailspin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;
import org.openjdk.jmh.annotations.Benchmark;
import tailspin.language.runtime.TailspinArray;

/**
 * A benchmark that uses a simple implementation of bubblesort.
 */
@SuppressWarnings("unused")
public class BubblesortBenchmark extends TruffleBenchmark {

  private static final String tailspinMain = """
      [50..1:-1 -> templates $! 100 - $! end] -> sortedCopy !
      """;

  private static final String tailspinIterate = """
      sortedCopy templates
        @ set $;
        $::length..2:-1 -> 2..$ -> !#
        $@ !
      
        when <|?($@($) matches <|..~$@($ - 1)>)> do
          temp is $@($);
          @($) set $@($ - 1);
          @($ - 1) set $temp;
      end sortedCopy
      """;

  private static final String tailspinRecurse = """
      sortedCopy templates
        bubble templates
          @ set 1;
          1..$-1 -> !#
          $@ !
      
          when <|?($@sortedCopy($+1) matches <|..~$@sortedCopy($)>)> do
            @ set $;
            temp is $@sortedCopy($@);
            @sortedCopy($@) set $@sortedCopy($@ + 1);
            @sortedCopy($@ + 1) set $temp;
        end bubble
      
        @ set $;
        $::length -> !#
        $@ !
      
        when <|2..> do $ -> bubble -> !#
      end sortedCopy
      """;

  @Benchmark
  public void sort_tailspin_iterate() {
    TailspinArray sorted = truffleContext.eval("tt", tailspinIterate + tailspinMain).as(TailspinArray.class);
    if (sorted.getArraySize() != 100) {
      throw new AssertionError("Too short array " + sorted.getArraySize());
    }
    for (int i = 1; i < sorted.getArraySize(); i++) {
      if ((long) sorted.getNative(i - 1, false) > (long) sorted.getNative(i, false)) {
        throw new AssertionError("Out of order " + sorted.getArraySize());
      }
    }
  }

  @Benchmark
  public void sort_tailspin_recurse() {
    TailspinArray sorted = truffleContext.eval("tt", tailspinRecurse + tailspinMain).as(TailspinArray.class);
    if (sorted.getArraySize() != 100) {
      throw new AssertionError("Too short array " + sorted.getArraySize());
    }
    for (int i = 1; i < sorted.getArraySize(); i++) {
      if ((long) sorted.getNative(i - 1, false) > (long) sorted.getNative(i, false)) {
        throw new AssertionError("Out of order " + sorted.getArraySize());
      }
    }
  }

  @Benchmark
  public void sort_java() {
    List<Long> input = LongStream.iterate(50L, i -> i > 0L, i -> i - 1L)
        .flatMap(i -> LongStream.of(i, 50L - i)).boxed()
        .toList();
    List<Long> output = sortedCopy(input);
    if (output.size() != 100) {
      throw new AssertionError("Too short array " + output.size());
    }
    for (int i = 1; i < output.size(); i++) {
      if (output.get(i - 1) > output.get(i)) {
        throw new AssertionError("Not sorted " + output);
      }
    }
  }

  public static List<Long> sortedCopy(List<Long> in) {
    List<Long> out = new ArrayList<>(in);
    for (int i = out.size(); i > 1; i--) {
      for (int j = 1; j < i; j++) {
        if (out.get(j - 1) > out.get(j)) {
          long temp = out.get(j - 1);
          out.set(j - 1, out.get(j));
          out.set(j, temp);
        }
      }
    }
    return out;
  }
}