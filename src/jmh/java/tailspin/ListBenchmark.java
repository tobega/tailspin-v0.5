package tailspin;

import org.openjdk.jmh.annotations.Benchmark;

/**
 * Modified List benchmark from "arewefastenoughyet" for SOM
 */
@SuppressWarnings("unused")
public class ListBenchmark extends TruffleBenchmark {
  // TODO: isShorterThan should be an operator
  // TODO: x, y, a, b, c could be local types and could just refer to <|next>
  private static final String tailspinProgram = """
      next requires <|{ val:, ?next: VOID}>
      x requires <|{ val:, ?next: VOID}>
      y requires <|{ val:, ?next: VOID}>
      a requires <|{ val:, ?next: VOID}>
      b requires <|{ val:, ?next: VOID}>
      c requires <|{ val:, ?next: VOID}>
      
      makeList templates
        when <|0~..> do
          { val: $, $ - 1 -> # -> { next: $ } } !
      end makeList

      isShorterThan templates
        when <|{x: <|{next:}>, y: <|{next:}>}> do
          { x: $(x:; next:), y: $(y:; next:) } -> # !
        when <|{y: <|{next: <|{}>}>}> do 1 !
        otherwise 0 !
      end isShorterThan

      tail templates
        when <|?({x: $(b:), y: $(a:)} -> isShorterThan matches <|=1>)> do
          { a: {a: $(a:; next:), b: $(b:), c: $(c:)} -> #,
            b: {a: $(b:; next:), b: $(c:), c: $(a:)} -> #,
            c: {a: $(c:; next:), b: $(a:), c: $(b:)} -> #
          } -> # !
        otherwise
          $(c:) !
      end tail
      
      length templates
        when <|{ next: }> do 1 + ($(next:) -> #) !
        otherwise 1 !
      end length
      
      { a: 15 -> makeList, b: 10 -> makeList, c: 6 -> makeList } -> tail -> length !
      """;

  private static final String tailspinProgramEmptyNext = """      
      makeList templates
        when <|0~..> do
          { val: $, next: [$ - 1 -> #] } !
      end makeList

      isShorterThan templates
        when <|{x: <|{next: <|[](1..)>}>, y: <|{next: <|[](1..)>}>}> do
          { x: $(x:; next:; 1), y: $(y:; next:; 1) } -> # !
        when <|{y: <|{next: <|[](1..)>}>}> do 1 !
        otherwise 0 !
      end isShorterThan

      tail templates
        when <|?({x: $(b:), y: $(a:)} -> isShorterThan matches <|=1>)> do
          { a: {a: $(a:; next:; 1), b: $(b:), c: $(c:)} -> #,
            b: {a: $(b:; next:; 1), b: $(c:), c: $(a:)} -> #,
            c: {a: $(c:; next:; 1), b: $(a:), c: $(b:)} -> #
          } -> # !
        otherwise
          $(c:) !
      end tail
      
      length templates
        when <|{ next: <|[](1..)>}> do 1 + ($(next:; 1) -> #) !
        otherwise 1 !
      end length
      
      { a: 15 -> makeList, b: 10 -> makeList, c: 6 -> makeList } -> tail -> length !
      """;

  private static final String jsProgram = """
function List() {}

List.prototype.benchmark = function () {
  var result = this.tail(this.makeList(15),
                         this.makeList(10),
                         this.makeList(6));
  return result.length();
};

List.prototype.makeList = function (length) {
  if (length === 0) {
    return null;
  } else {
    var e = new Element(length);
    e.next = this.makeList(length - 1);
    return e;
  }
};

List.prototype.isShorterThan = function (x, y) {
  var xTail = x,
    yTail   = y;

  while (yTail !== null) {
    if (xTail === null) { return true; }
    xTail = xTail.next;
    yTail = yTail.next;
  }
  return false;
};

List.prototype.tail = function (x, y, z) {
  if (this.isShorterThan(y, x)) {
    return this.tail(this.tail(x.next, y, z),
      this.tail(y.next, z, x),
      this.tail(z.next, x, y));
  } else {
    return z;
  }
};

List.prototype.verifyResult = function (result) {
  return 10 === result;
};

function Element(v) {
  this.val  = v;
  this.next = null;
}

Element.prototype.length = function () {
  if (this.next === null) {
    return 1;
  } else {
    return 1 + this.next.length();
  }
};

new List().benchmark();
      """;

  @Benchmark
  public void list_javascript() {
    long length = truffleContext.eval("js", jsProgram).asLong();
    if (length != 10) throw new AssertionError("Bad length " + length);
  }

  @Benchmark
  public void list_tailspin_optional() {
    long length = truffleContext.eval("tt", tailspinProgram).asLong();
    if (length != 10) throw new AssertionError("Bad length " + length);
  }

  @Benchmark
  public void list_tailspin_empty() {
    long length = truffleContext.eval("tt", tailspinProgramEmptyNext).asLong();
    if (length != 10) throw new AssertionError("Bad length " + length);
  }


  @Benchmark
  public void list_java() {
    Element result = tail(new Triple(makeList(15), makeList(10), makeList(6)));
    if (length(result) != 10) throw new AssertionError("Bad length " + result);
  }

  record Element(Object val, Element next){}

  int length(Element e) {
    if (e.next() == null) {
      return 1;
    } else {
      return 1 + length(e.next());
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

  record Triple(Element a, Element b, Element c) {}

  Element tail(Triple t) {
    if (isShorterThan(t.b, t.a)) {
      return tail(
          new Triple(
              tail(new Triple(t.a.next(), t.b, t.c)),
              tail(new Triple(t.b.next(), t.c, t.a)),
              tail(new Triple(t.c.next(), t.a, t.b))));
    } else {
      return t.c;
    }
  }
}
