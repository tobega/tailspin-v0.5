package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.BlockNode.ElementExecutor;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.source.SourceSection;
import java.util.List;
import tailspin.language.nodes.StatementNode;

public class BlockNode extends StatementNode implements ElementExecutor<StatementNode> {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private com.oracle.truffle.api.nodes.BlockNode<StatementNode> block;

  private BlockNode(List<StatementNode> statements, SourceSection sourceSection) {
    super(sourceSection);
    this.block = com.oracle.truffle.api.nodes.BlockNode
        .create(statements.toArray(new StatementNode[0]), this);
  }

  public static BlockNode create(List<StatementNode> statements, SourceSection sourceSection) {
    return new BlockNode(statements, sourceSection);
  }


  @Override
  @ExplodeLoop
  public void executeVoid(VirtualFrame frame) {
    block.executeVoid(frame, 0);
  }

  @Override
  public void executeVoid(VirtualFrame frame, StatementNode statement, int index, int argument) {
    statement.executeVoid(frame);
  }
}
