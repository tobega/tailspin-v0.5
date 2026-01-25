package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.CV_SLOT;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.PreconditionFailed;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.ReadContextValueNode;

public abstract class PreconditionNode extends StatementNode {
  @Child
  @Executed
  @SuppressWarnings("FieldMayBeFinal")
  ValueNode cvNode;

  @Child
  @SuppressWarnings("FieldMayBeFinal")
  private MatcherNode precondition;

  PreconditionNode(ValueNode cvNode, MatcherNode precondition, SourceSection sourceSection) {
    super(sourceSection);
    this.cvNode = cvNode;
    this.precondition = precondition;
  }

  @Specialization
  public void doLong(VirtualFrame frame, long value) {
    if (precondition.executeMatcherLong(frame, value)) return;
    throw new PreconditionFailed("Requirement not met by " + value, precondition);
  }

  @Specialization
  public void doGeneric(VirtualFrame frame, Object value) {
    if (precondition.executeMatcherGeneric(frame, value)) return;
    throw new PreconditionFailed("Requirement not met by " + value, precondition);
  }

  public static PreconditionNode create(MatcherNode precondition, SourceSection sourceSection) {
    return PreconditionNodeGen.create(ReadContextValueNode.create(-1, CV_SLOT), precondition, sourceSection);
  }
}
