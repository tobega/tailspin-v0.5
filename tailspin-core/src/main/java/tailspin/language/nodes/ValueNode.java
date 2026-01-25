package tailspin.language.nodes;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.source.SourceSection;

@TypeSystemReference(TailspinTypes.class)
public abstract class ValueNode extends TailspinNode {

  public ValueNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
    return TailspinTypesGen.expectLong(executeGeneric(frame));
  }

  public abstract Object executeGeneric(VirtualFrame frame);
}
