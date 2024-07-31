package tailspin;

import org.openjdk.jmh.annotations.Benchmark;

/**
 * Modified List benchmark from "arewefastenoughyet" for SOM
 */
public class ListBenchmark extends TruffleBenchmark {
  private static final String tailspinProgram = """
      makeList templates
        <|0~..>
          { val: $, $ - 1 -> makeList -> { next: $ } } !
      end makeList

      isShorterThan templates
        when <|{x: <|{next: <{}>}>, y: <|{next: <{}>}>}> do
          { x: $(x:; next:), y: $(y:; next:) } -> #
        when <|{y: <|{next: <{}>}>}> do 1 !
        otherwise 0 !
      end isShorterThan

      tail templates
        when <?({x: $(b:), y: $(a:)} -> isShorterThan <|=1>)> do
          { a: {a: $(a:; next:), b: $(b:), c: $(c:)} -> #,
            b: {a: $(b:; next:), b: $(c:), c: $(a:)} -> #,
            c: {a: $(c:; next:), b: $(a:), c: $(b:)} -> #
          } -> # !
        otherwise
          $(c:) !
      end tail
      
      { a: 15 -> makeList, b: 10 -> makeList, c: 6 -> makeList } -> tail -> $::length !
      """;


  @Benchmark
  public void list_java() {
    Element result = tail(makeList(15), makeList(10), makeList(6));
    if (length(result) != 10) throw new AssertionError("Bad length " + result);
  }

  record Element(Object val, Element next){}

  int length(Element e) {
    if (e.next == null) {
      return 1;
    } else {
      return 1 + length(e.next);
    }
  }

  Element makeList(int length) {
    if (length == 0) {
      return null;
    } else {
      return new Element(length, makeList(length - 1));
    }
  }

  boolean isShorterThan(final Element x, final Element y) {
    Element xTail = x;
    Element yTail = y;

    while (yTail != null) {
      if (xTail == null) return true;
      xTail = xTail.next();
      yTail = yTail.next();
    }
    return false;
  }

  Element tail(Element a, Element b, Element c) {
    if (isShorterThan(b, a)) {
      return tail(tail(a.next(), b, c),
          tail(b.next(), c, a),
          tail(c.next(), a, b));
    } else {
      return c;
    }
  }
}
