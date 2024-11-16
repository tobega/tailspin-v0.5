package tailspin.language.nodes.value;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.ValueNode;

public class VoidValue extends ValueNode {

  public VoidValue(SourceSection sourceSection) {
    super(sourceSection);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }

  public static VoidValue create(SourceSection sourceSection) {
    return new VoidValue(sourceSection);
  }
}
