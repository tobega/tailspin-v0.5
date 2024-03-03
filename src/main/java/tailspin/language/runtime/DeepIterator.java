package tailspin.language.runtime;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DeepIterator implements Iterator<Object> {
  private final Deque<Iterator<?>> iterators = new ArrayDeque<>();
  private Object nextElement;

  public DeepIterator(Iterator<Object> iterator) {
    iterators.push(iterator);
  }

  @Override
  public boolean hasNext() {
    if (nextElement == null) getNextElement();
    return nextElement != null;
  }

  @Override
  public Object next() {
    if (nextElement == null) getNextElement();
    if (nextElement == null) throw new NoSuchElementException();
    Object element = nextElement;
    nextElement = null;
    return element;
  }

  private void getNextElement() {
    while (!iterators.isEmpty()) {
      if (iterators.peek().hasNext()) {
        nextElement = iterators.peek().next();
        if (nextElement instanceof Iterator<?> iterator) {
          iterators.push(iterator);
          nextElement = null;
        } else {
          return;
        }
      } else {
        iterators.pop();
      }
    }
  }
}
