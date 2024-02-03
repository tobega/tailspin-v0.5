package tailspin.language.literals;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import tailspin.language.ExpressionNode;

public class IntegerLiteral extends ExpressionNode {
  private final long value;

  public IntegerLiteral(long value) {
    this.value = value;
  }

  @Override
  public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
    return value;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return value;
  }
}
