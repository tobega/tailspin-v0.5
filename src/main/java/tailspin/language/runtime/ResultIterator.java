package tailspin.language.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.util.Arrays;

@ExportLibrary(InteropLibrary.class)
public class ResultIterator implements TruffleObject {
  private Object[] elements;
  private int end;
  private int current;

  private ResultIterator(Object[] values) {
    elements = values;
  }

  public static ResultIterator of(Object[] values) {
    ResultIterator iterator = new ResultIterator(values);
    iterator.end = values.length;
    iterator.current = 0;
    return iterator;
  }

  public static ResultIterator empty() {
    ResultIterator iterator = new ResultIterator(new Object[10]);
    iterator.end = 0;
    iterator.current = 0;
    return iterator;
  }

  public static Object merge(Object previous, Object result) {
    if (previous == null) return result;
    if (result == null) return previous;
    ResultIterator merged;
    if (previous instanceof ResultIterator ri) {
      merged = ri;
    } else {
      merged = ResultIterator.empty();
      merged.addObject(previous);
    }
    if (result instanceof ResultIterator ri) {
      merged.add(ri);
    } else {
      merged.addObject(result);
    }
    return merged;
  }

  @ExportMessage
  public boolean isIterator() {
    return true;
  }

  @ExportMessage
  public boolean hasIteratorNextElement() {
    return current < end;
  }

  @ExportMessage
  public Object getIteratorNextElement() throws StopIterationException {
    if (hasIteratorNextElement()) {
      Object result = elements[current];
      current++;
      return result;
    }
    throw StopIterationException.create();
  }

  public void add(ResultIterator results) {
    if (elements.length < end + results.end) {
      elements = Arrays.copyOf(elements, end + results.end + 10);
    }
    System.arraycopy(results.elements, 0, elements, end, results.end);
    end += results.end;
  }

  public void addObject(Object result) {
    if (result instanceof ResultIterator) throw new AssertionError("Trying to add result iterator as object");
    if (end == elements.length) {
      elements = Arrays.copyOf(elements, end + 10);
    }
    elements[end] = result;
    end++;
  }
}
