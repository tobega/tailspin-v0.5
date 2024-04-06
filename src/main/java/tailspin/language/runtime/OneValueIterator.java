package tailspin.language.runtime;

import java.util.Iterator;

public class OneValueIterator implements Iterator<Object> {
  private Object item;

  public OneValueIterator(Object item) {
    this.item = item;
  }

  @Override
  public boolean hasNext() {
    return item != null;
  }

  @Override
  public Object next() {
    Object result = item;
    item = null;
    return result;
  }
}
