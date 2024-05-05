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

  @Override
  public int getValueCount() {
    return (int) ((end + increment - current) / increment);
  }

  @ExportMessage
  public boolean isIterator() {
    return true;
  }

  @Override
  @ExportMessage
  public boolean hasIteratorNextElement() {
    return current <= end;
  }

  @Override
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
