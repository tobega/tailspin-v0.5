package tailspin.language.nodes;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

@TypeSystemReference(TailspinTypes.class)
public abstract class ExpressionNode extends Node {
  public abstract Object executeGeneric(VirtualFrame frame);

  public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
    return TailspinTypesGen.expectLong(executeGeneric(frame));
  }
}
