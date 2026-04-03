package tailspin.language.runtime.stream;

import java.util.Arrays;
import java.util.NoSuchElementException;

public final class ListStream {
  private static final Object[] EMPTY = new Object[0];
  private Object[] elements;
  private int size = 0;
  private int index = 0;

  public ListStream() {
    this.elements = EMPTY;
  }

  public ListStream(Object[] items) {
    this.elements = items;
    this.size = items.length;
  }

  public int size() {
    return size;
  }

  public boolean hasNext() { return index < size; }

  public Object next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    return elements[index++];
  }

  public void append(Object item) {
    append(item, elements);
  }

  public void merge(ListStream other) {
    appendAll(other, elements);
  }

  public void prepend(Object item) {
    prepend(item, elements);
  }

  public Object[] getArray() {
    return elements;
  }

  private void append(Object item, Object[] elements) {
    if (elements.length == size) elements = grow();
    elements[size++] = item;
  }

  private void prepend(Object item, Object[] elements) {
    if (elements.length == size) elements = grow();
    System.arraycopy(elements, 0, elements, 1, size);
    elements[0] = item;
    size++;
  }

  private Object[] grow() {
    if (elements == EMPTY) {
      return elements = new Object[10];
    } else {
      return elements = grow(elements.length + 1);
    }
  }

  private void appendAll(ListStream other, Object[] elements) {
    int newSize = this.size + other.size;
    if (newSize > elements.length) elements = grow(newSize);
    System.arraycopy(other.elements, 0, elements, this.size, other.size);
    this.size = newSize;
  }

  private Object[] grow(int newSize) {
    return elements = Arrays.copyOf(elements, newSize + (newSize >> 1));
  }
}
