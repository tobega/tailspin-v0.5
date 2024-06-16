package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.EMIT_SLOT;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.LocalDefinitionNode;

public class TemplatesRootNode extends RootNode {

  public static final int DEFINING_SCOPE_ARG = 0;
  private static final int CV_ARG = 1;
  private static final int RESULT_BUILDER_ARG = 2;

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private StatementNode setCurrentValue;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private StatementNode statement;
  private TemplatesRootNode(TruffleLanguage<?> language,
      FrameDescriptor frameDescriptor, StatementNode statement) {
    super(language, frameDescriptor);
    this.setCurrentValue = LocalDefinitionNode.create(new ReadArgumentNode(CV_ARG), CV_SLOT);
    this.statement = statement;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    setCurrentValue.executeVoid(frame);
    frame.setObjectStatic(EMIT_SLOT, frame.getArguments()[RESULT_BUILDER_ARG]);
    statement.executeVoid(frame);
    Object results = frame.getObjectStatic(EMIT_SLOT);
    frame.setObjectStatic(EMIT_SLOT, null);
    return results;
  }

  public static CallTarget create(FrameDescriptor fd, StatementNode body) {
    return new TemplatesRootNode(null, fd,
        body).getCallTarget();
  }

  public static class ReadArgumentNode extends ValueNode {
    final int argIndex;

    public ReadArgumentNode(int argIndex) {
      this.argIndex = argIndex;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
      return frame.getArguments()[argIndex];
    }
  }
}
