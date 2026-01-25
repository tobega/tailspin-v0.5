package tailspin.language.nodes.state;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Cached.Exclusive;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.TailspinArray;

@GenerateInline
public abstract class FreezeNode extends Node {
  public abstract void executeFreeze(Node node, Object value);

  @Specialization(guards = "ignored == null")
  void doNull(Object ignored) {}

  @Specialization
  void doArray(TailspinArray ta,
      @Cached(inline = false) @Exclusive FreezeNode childFreezer) {
    if (ta.needsFreeze()) {
      long length = ta.getArraySize();
      for (int i = 0; i < length; i++) {
        childFreezer.executeFreeze(this, ta.getNative(i, false));
      }
    }
  }

  // TODO: investigate caching array of freezers per key on shape
  @Specialization
  void doStructure(Structure s,
      @CachedLibrary(limit = "2") DynamicObjectLibrary dol,
      @Cached(inline = false) @Exclusive FreezeNode childFreezer) {
    if (s.needsFreeze()) {
      for (Object key : dol.getKeyArray(s)) {
        Object child = dol.getOrDefault(s, key, null);
        childFreezer.executeFreeze(this, child);
      }
    }
  }

  @Specialization
  void doObject(Object ignored) {}
}
