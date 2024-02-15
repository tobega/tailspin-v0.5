package tailspin.language.runtime;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class OneResultValue implements Iterator<Object> {
  private boolean accessed = false;
  private final Object value;

  public OneResultValue(Object value) {
    this.value = value;
  }

  @Override
  public boolean hasNext() {
    return !accessed;
  }

  @Override
  public Object next() {
    if (accessed) {
      throw new NoSuchElementException();
    }
    accessed = true;
    return value;
  }
}
