package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TransformNode;

public class StatementTransformNode extends TransformNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  StatementNode statement;

  StatementTransformNode(StatementNode statement, SourceSection sourceSection) {
    super(sourceSection);
    this.statement = statement;
  }

  public static StatementTransformNode create(StatementNode statement, SourceSection sourceSection) {
    return new StatementTransformNode(statement, sourceSection);
  }

  @Override
  public void executeTransform(VirtualFrame frame) {
    statement.executeVoid(frame);
  }
}
