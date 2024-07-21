package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.StatementNode;

public class DoNothingNode extends StatementNode {

  @Override
  public void executeVoid(VirtualFrame frame) {
    // do nothing
  }

  public static DoNothingNode create() {
    return new DoNothingNode();
  }
}
