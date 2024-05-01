package tailspin.language.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
public class DecreasingIntegerRangeIterator implements TruffleObject, ValueStream {
  private final long end;
  private final long decrement;
  private long current;

  public DecreasingIntegerRangeIterator(long start, long end, long decrement) {
    this.end = end;
    if (decrement < 0) throw new AssertionError("Negative decrement");
    this.decrement = decrement;
    current = start;
  }

  public Object[] asArray() {
    int length = (int) ((current + decrement - end) / decrement);
    Object[] result = new Object[length];
    for (int i = 0; i < length; i++) {
      result[i] = current;
      current -= decrement;
    }
    return result;
  }

  @ExportMessage
  public boolean isIterator() {
    return true;
  }

  @ExportMessage
  public boolean hasIteratorNextElement() {
    return current >= end;
  }

  @ExportMessage
  public Object getIteratorNextElement() throws StopIterationException {
    if (hasIteratorNextElement()) {
      Object result = current;
      current -= decrement;
      return result;
    }
    throw StopIterationException.create();
  }
}
