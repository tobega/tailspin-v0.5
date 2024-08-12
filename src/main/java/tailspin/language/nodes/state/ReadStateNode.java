package tailspin.language.nodes.state;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import tailspin.language.nodes.ValueNode;

@NodeChild(type = ValueNode.class)
public abstract class ReadStateNode extends ValueNode {

  @Specialization
  public Object doFreezeRead(Object value,
      @Cached(inline = true) FreezeNode freezer) {
    freezer.executeFreeze(this, value);
    return value;
  }

  public static ReadStateNode create(ValueNode value) {
    return ReadStateNodeGen.create(value);
  }
}
