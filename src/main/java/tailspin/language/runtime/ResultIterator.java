package tailspin.language.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
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

  @ExportMessage
  public boolean isIterator() {
    return true;
  }

  @ExportMessage
  public boolean hasIteratorNextElement() {
    return current < end;
  }

  @ExportMessage
  public Object getIteratorNextElement() {
    if (hasIteratorNextElement()) {
      Object result = elements[current];
      current++;
      return result;
    }
    return null;
  }

  public void add(ResultIterator results) {
    if (elements.length < end + results.end) {
      elements = Arrays.copyOf(elements, end + results.end + 10);
    }
    System.arraycopy(results.elements, 0, elements, end, results.end);
    end += results.end;
  }
}
