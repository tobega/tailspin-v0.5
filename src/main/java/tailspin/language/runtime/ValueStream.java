package tailspin.language.runtime;

import com.oracle.truffle.api.interop.StopIterationException;

public interface ValueStream {
  int getValueCount();
  boolean hasIteratorNextElement();
  Object getIteratorNextElement() throws StopIterationException;
}
