package tailspin.language.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import java.math.BigInteger;
import tailspin.language.nodes.ExpressionNode;

public class BigIntegerLiteral extends ExpressionNode {
  private final BigInteger value;

  public BigIntegerLiteral(BigInteger value) {
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
}
