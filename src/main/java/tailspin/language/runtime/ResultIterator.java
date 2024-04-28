package tailspin.language.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.util.Arrays;

@ExportLibrary(InteropLibrary.class)
public class ResultIterator implements TruffleObject, ValueStream {
  private static final int EXTRA = 10;
  private Object[] elements;
  private int end;
  private int current;

  private ResultIterator(Object[] values) {
    elements = values;
  }

  public Object[] asArray() {
    return Arrays.copyOf(elements, end);
  }

  public static ResultIterator empty() {
    ResultIterator iterator = new ResultIterator(new Object[EXTRA]);
    iterator.end = 0;
    iterator.current = 0;
    return iterator;
  }

  public static Object merge(Object previous, Object result) {
      if (previous == null)
        return result;
      if (result == null)
        return previous;
      ResultIterator merged;
      if (previous instanceof ResultIterator ri) {
        merged = ri;
      } else {
        merged = ResultIterator.empty();
        if (previous instanceof ValueStream vs) {
          merged.addArray(vs.asArray());
        } else {
          merged.addObject(previous);
        }
      }
      if (result instanceof ResultIterator ri) {
        merged.add(ri);
      } else if (result instanceof ValueStream vs) {
        merged.addArray(vs.asArray());
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

  private void add(ResultIterator results) {
    if (elements.length < end + results.end) {
      elements = Arrays.copyOf(elements, end + results.end + EXTRA);
    }
    System.arraycopy(results.elements, 0, elements, end, results.end);
    end += results.end;
  }

  private void addArray(Object[] results) {
    if (elements.length < end + results.length) {
      elements = Arrays.copyOf(elements, end + results.length + EXTRA);
    }
    System.arraycopy(results, 0, elements, end, results.length);
    end += results.length;
  }

  private void addObject(Object result) {
    if (result instanceof ResultIterator) throw new AssertionError("Trying to add result iterator as object");
    if (end == elements.length) {
      elements = Arrays.copyOf(elements, end + EXTRA);
    }
    elements[end] = result;
    end++;
  }

  public long getEnd() {
    return end;
  }

  public Object[] getArray() {
    return elements;
  }
}
