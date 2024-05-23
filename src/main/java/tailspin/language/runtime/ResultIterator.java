package tailspin.language.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.util.Arrays;

@ExportLibrary(InteropLibrary.class)
public class ResultIterator implements TruffleObject {
  private static final int EXTRA = 10;
  private Object[] elements;
  private int end;
  private int current;

  private ResultIterator(Object[] values) {
    elements = values;
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
        merged.addObject(previous);
      }
      merged.addObject(result);
      return merged;
  }

  @ExportMessage
  public boolean isIterator() {
    return true;
  }

  @ExportMessage
  public boolean hasIteratorNextElement() {
    while (current < end && elements[current] instanceof ResultIterator ri && !ri.hasIteratorNextElement())
      current++;
    return current < end;
  }

  @ExportMessage
  public Object getIteratorNextElement() throws StopIterationException {
    if (!hasIteratorNextElement()) {
      throw StopIterationException.create();
    }
    if (elements[current] instanceof ResultIterator ri) {
      return ri.getIteratorNextElement();
    }
    Object result = elements[current];
    current++;
    return result;
  }

  public void addObject(Object result) {
    if (end == elements.length) {
      elements = Arrays.copyOf(elements, end + EXTRA);
    }
    elements[end] = result;
    end++;
  }

  public int getValueCount() {
    int count = 0;
    for (int i = 0; i < end; i++) {
      if (elements[i] instanceof ResultIterator ri) {
        count += ri.getValueCount();
      } else {
        count++;
      }
    }
    return count;
  }

  public Object[] getValueArray() {
    Object[] result = new Object[getValueCount()];
    for (int i = 0; hasIteratorNextElement(); i++) {
      try {
        result[i] = getIteratorNextElement();
      } catch (StopIterationException e) {
        throw new RuntimeException(e);
      }
    }
    return result;
  }
}
