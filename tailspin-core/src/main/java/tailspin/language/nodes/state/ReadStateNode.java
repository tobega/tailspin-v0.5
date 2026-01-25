package tailspin.language.nodes.state;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.ValueNode;

@NodeChild(type = ValueNode.class)
public abstract class ReadStateNode extends ValueNode {

  public ReadStateNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  @Specialization
  public Object doFreezeRead(Object value,
      @Cached(inline = true) FreezeNode freezer) {
    freezer.executeFreeze(this, value);
    return value;
  }

  public static ReadStateNode create(ValueNode value, SourceSection sourceSection) {
    return ReadStateNodeGen.create(sourceSection, value);
  }
}
