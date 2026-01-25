package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.SciNum;

public class SciNumLiteral extends ValueNode {

  private final SciNum value;

  public SciNumLiteral(SciNum value, SourceSection sourceSection) {
    super(sourceSection);
    this.value = value;
  }

  @Override
  public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
    throw new UnexpectedResultException(value);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return value;
  }

  public static ValueNode create(SciNum value, SourceSection sourceSection) {
    return new SciNumLiteral(value, sourceSection);
  }
}
