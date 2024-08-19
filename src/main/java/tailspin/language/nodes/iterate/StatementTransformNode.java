package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TransformNode;

public class StatementTransformNode extends TransformNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  StatementNode statement;

  StatementTransformNode(StatementNode statement) {
    this.statement = statement;
  }

  public static StatementTransformNode create(StatementNode statement) {
    return new StatementTransformNode(statement);
  }

  @Override
  public void executeTransform(VirtualFrame frame) {
    statement.executeVoid(frame);
  }
}
