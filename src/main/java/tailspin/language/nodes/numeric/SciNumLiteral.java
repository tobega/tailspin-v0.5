package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.SciNum;

public class SciNumLiteral extends ValueNode {

  private final SciNum value;

  public SciNumLiteral(SciNum value) {
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

  public static ValueNode create(SciNum value) {
    return new SciNumLiteral(value);
  }
}
