package tailspin.language.nodes.numeric;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.matchers.NumericTypeMatcherNode;

public class IntegerLiteral extends ValueNode {
  private final long value;

  private IntegerLiteral(long value) {
    this.value = value;
  }

  public static IntegerLiteral create(long l) {
    return new IntegerLiteral(l);
  }

  @Override
  public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
    return value;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return value;
  }

  @Override
  public MatcherNode getTypeMatcher() {
    return NumericTypeMatcherNode.create();
  }
}
