package tailspin.language.nodes.state;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.runtime.TailspinArray;

@GenerateInline
public abstract class FreezeNode extends Node {
  public abstract void executeFreeze(Node node, Object value);

  @Specialization
  void doArray(TailspinArray ta,
      @Cached(inline = false) FreezeNode childFreezer) {
    if (ta.needsFreeze()) {
      long length = ta.getArraySize();
      for (int i = 0; i < length; i++) {
        childFreezer.executeFreeze(this, ta.getNative(i));
      }
    }
  }

  @Specialization
  void doObject(Object ignored) {}
}
