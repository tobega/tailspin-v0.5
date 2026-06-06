package tailspin.language.runtime.stream;

 import com.oracle.truffle.api.dsl.Bind;
 import com.oracle.truffle.api.dsl.Cached;
 import com.oracle.truffle.api.dsl.Cached.Shared;
 import com.oracle.truffle.api.dsl.Specialization;
 import com.oracle.truffle.api.interop.InteropLibrary;
 import com.oracle.truffle.api.interop.StopIterationException;
 import com.oracle.truffle.api.interop.TruffleObject;
 import com.oracle.truffle.api.interop.UnsupportedMessageException;
 import com.oracle.truffle.api.library.CachedLibrary;
 import com.oracle.truffle.api.library.ExportLibrary;
 import com.oracle.truffle.api.library.ExportMessage;
 import com.oracle.truffle.api.nodes.Node;
 import tailspin.language.nodes.iterate.GetNextRangeValueNode;
 
 @ExportLibrary(InteropLibrary.class)
 public class RangeStream implements TruffleObject {
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

   Object pendingElement;
   @ExportMessage
   boolean isIterator() { return true; }
   @ExportMessage
   static class HasIteratorNextElement {
     @Specialization
     static boolean doHasNext(RangeStream stream,
         @Bind("$node") Node node,
         @Cached(allowUncached = true, neverDefault = true) GetNextRangeValueNode nextNode) {
       if (stream.pendingElement != null) {
         return true;
       }
         stream.pendingElement = nextNode.execute(null, node, stream);
         return stream.pendingElement != null;
     }
   }
 
   @ExportMessage
   Object getIteratorNextElement(@CachedLibrary(limit = "2") @Shared InteropLibrary interopLibrary) throws StopIterationException, UnsupportedMessageException {
     if (pendingElement == null && !interopLibrary.hasIteratorNextElement(this)) {
       throw StopIterationException.create();
       }
       Object value = pendingElement;
       pendingElement = null;
       return value;
   }
 
   @ExportMessage
   Object toDisplayString(boolean allowSideEffects, @CachedLibrary(limit = "2") @Shared InteropLibrary interopLibrary) {
     if (!allowSideEffects) {
       return next + ".." + (inclusiveEnd ? "" : "~") + ":" + increment;
     }
     StringBuilder result = new StringBuilder();
     try {
     while (interopLibrary.hasIteratorNextElement(this)) {
       try {
         result.append(interopLibrary.getIteratorNextElement(this)).append(", ");
       } catch (StopIterationException e) {
         break;
       }
     }
     } catch (UnsupportedMessageException e) {
       throw new RuntimeException(e);
     }
     if (result.length() > 0) {
       result.delete(result.length() - 2, result.length());
     }
     return result.toString();
   }
}
