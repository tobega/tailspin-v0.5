package tailspin.language.runtime.stream;

public final class ListStream {
  private final Object[] items;
  private int index = 0;

  public ListStream(Object[] items) { this.items = items; }

  public boolean hasNext() { return index < items.length; }

  public Object next() { return items[index++]; }
}
