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

  @Override
  public int getValueCount() {
    return (int) ((current + decrement - end) / decrement);
  }

  @ExportMessage
  public boolean isIterator() {
    return true;
  }

  @Override
  @ExportMessage
  public boolean hasIteratorNextElement() {
    return current >= end;
  }

  @Override
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
