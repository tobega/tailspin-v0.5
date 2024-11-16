package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.StatementNode;

public class DoNothingNode extends StatementNode {

  public DoNothingNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    // do nothing
  }

  public static DoNothingNode create(SourceSection sourceSection) {
    return new DoNothingNode(sourceSection);
  }
}
