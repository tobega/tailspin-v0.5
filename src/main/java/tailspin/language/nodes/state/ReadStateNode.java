package tailspin.language.nodes.state;

import static tailspin.language.runtime.Templates.STATE_SLOT;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.ReadContextValueNode;

@NodeChild(type = ValueNode.class)
public abstract class ReadStateNode extends ValueNode {

  @Specialization
  public Object doFreezeRead(Object value,
      @Cached(inline = true) FreezeNode freezer) {
    freezer.executeFreeze(this, value);
    return value;
  }

  public static ReadStateNode create(int level) {
    return ReadStateNodeGen.create(
        ReadContextValueNode.create(level, STATE_SLOT));
  }
}
