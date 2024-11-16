package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.source.SourceSection;
import java.math.BigInteger;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;

public class BigIntegerLiteral extends ValueNode {
  private final BigInteger value;

  public BigIntegerLiteral(BigInteger value, SourceSection sourceSection) {
    super(sourceSection);
    this.value = value;
  }

  public static BigIntegerLiteral create(BigInteger value, SourceSection sourceSection) {
    return new BigIntegerLiteral(value, sourceSection);
  }

  @Override
  public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
    throw new UnexpectedResultException(value);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return new BigNumber(value);
  }
}
