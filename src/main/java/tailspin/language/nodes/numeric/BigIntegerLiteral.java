package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import java.math.BigInteger;
import tailspin.language.nodes.ValueNode;

public class BigIntegerLiteral extends ValueNode {
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
