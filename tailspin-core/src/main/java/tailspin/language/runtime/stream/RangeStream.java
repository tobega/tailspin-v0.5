package tailspin.language.runtime.stream;

public class RangeStream {
  public Object next;
  public final Object end;
  public final Object increment;
  public final boolean inclusiveEnd;
  public RangeStream(Object start, Object end, Object increment, boolean inclusiveEnd) {
    this.next = start;
    this.end = end;
    this.increment = increment;
    this.inclusiveEnd = inclusiveEnd;
  }
}
