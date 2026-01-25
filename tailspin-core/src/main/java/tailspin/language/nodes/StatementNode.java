package tailspin.language.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;

public abstract class StatementNode extends TailspinNode {

  public StatementNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  public abstract void executeVoid(VirtualFrame frame);
}
