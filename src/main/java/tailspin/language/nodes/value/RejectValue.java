package tailspin.language.nodes.value;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.RejectSinkReached;
import tailspin.language.nodes.ValueNode;

public class RejectValue extends ValueNode {

  public RejectValue(SourceSection sourceSection) {
    super(sourceSection);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    throw new RejectSinkReached("Input rejected", this);
  }

  public static RejectValue create(SourceSection sourceSection) {
    return new RejectValue(sourceSection);
  }
}
