package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import java.util.List;
import tailspin.language.nodes.StatementNode;

public class BlockNode extends StatementNode {
  @Children
  private final StatementNode[] statements;

  public BlockNode(List<StatementNode> statements) {
    this.statements = statements.toArray(new StatementNode[0]);
  }


  @Override
  @ExplodeLoop
  public void executeVoid(VirtualFrame frame) {
    for (StatementNode statement : statements) {
      statement.executeVoid(frame);
    }
  }
}
