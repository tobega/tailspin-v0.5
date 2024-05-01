package tailspin.language.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
public class IncreasingIntegerRangeIterator implements TruffleObject, ValueStream {
  private final long end;
  private final long increment;
  private long current;

  public IncreasingIntegerRangeIterator(long start, long end, long increment) {
    this.end = end;
    this.increment = increment;
    current = start;
  }

  public Object[] asArray() {
    int length = (int) ((end + increment - current) / increment);
    Object[] result = new Object[length];
    for (int i = 0; i < length; i++) {
      result[i] = current;
      current += increment;
    }
    return result;
  }

  @ExportMessage
  public boolean isIterator() {
    return true;
  }

  @ExportMessage
  public boolean hasIteratorNextElement() {
    return current <= end;
  }

  @ExportMessage
  public Object getIteratorNextElement() throws StopIterationException {
    if (hasIteratorNextElement()) {
      Object result = current;
      current += increment;
      return result;
    }
    throw StopIterationException.create();
  }
}
