package tailspin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;
import org.openjdk.jmh.annotations.Benchmark;
import tailspin.language.runtime.TailspinArray;

/**
 * Tests list streaming and list tailing
 */
@SuppressWarnings("unused")
public class SelectionSortBenchmark extends TruffleBenchmark {
  private static final String tailspinProgram = """
      select-sort templates
        @ set [];
        $ -> # !
        when <|[](=0)> do
          $@ !
        otherwise
          $ -> auxiliary templates
            @ set $(1);
            [$(~..)... -> #] !
            ..|@select-sort set $@;
            when <|$@..> do $ !
            otherwise
              $@ !
              @ set $;
          end -> # !
      end select-sort
      
      [50..1:-1 -> templates $! 100 - $! end] -> select-sort !
      """;

  @Benchmark
  public void selection_sort_tailspin() {
    TailspinArray sorted = truffleContext.eval("tt", tailspinProgram).as(TailspinArray.class);
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
  public static void selection_sort_java() {
    List<Long> input = LongStream.iterate(50L, i -> i > 0L, i -> i - 1L)
        .flatMap(i -> LongStream.of(i, 50L - i)).boxed()
        .toList();
    List<Long> output = selectionSort(input);
    if (output.size() != 100) {
      throw new AssertionError("Too short array " + output.size());
    }
    for (int i = 1; i < output.size(); i++) {
      if (output.get(i - 1) > output.get(i)) {
        throw new AssertionError("Not sorted " + output);
      }
    }
  }

  public static List<Long> selectionSort(List<Long> in) {
    List<Long> out = new ArrayList<>();
    while (!in.isEmpty()) {
      AtomicLong min = new AtomicLong(in.getFirst());
      in = in.subList(1, in.size()).stream().map((v) -> {
        if (v >= min.get()) {
          return v;
        } else {
          return min.getAndSet(v);
        }
      }).toList();
      out.addLast(min.get());
    }
    return out;
  }
}