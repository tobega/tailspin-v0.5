package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.EMIT_SLOT;
import static tailspin.language.runtime.Templates.SCOPE_SLOT;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.WriteContextValueNode;

public class TemplatesRootNode extends RootNode {

  public static final int DEFINING_SCOPE_ARG = 0;
  private static final int CV_ARG = 1;
  private static final int RESULT_BUILDER_ARG = 2;

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private StatementNode setCurrentValue;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private StatementNode createScope;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private StatementNode statement;
  private TemplatesRootNode(TruffleLanguage<?> language,
      FrameDescriptor frameDescriptor, FrameDescriptor scopeDescriptor, StatementNode statement) {
    super(language, frameDescriptor);
    this.createScope = scopeDescriptor == null ? CopyScopeNode.create() : CreateScopeNode.create(scopeDescriptor);
    this.setCurrentValue = WriteContextValueNode.create(-1, CV_SLOT, new ReadArgumentNode(CV_ARG));
    this.statement = statement;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    createScope.executeVoid(frame);
    setCurrentValue.executeVoid(frame);
    frame.setObjectStatic(EMIT_SLOT, frame.getArguments()[RESULT_BUILDER_ARG]);
    statement.executeVoid(frame);
    Object results = frame.getObjectStatic(EMIT_SLOT);
    frame.setObjectStatic(EMIT_SLOT, null);
    return results;
  }

  // scopeDescriptor is null for matchers and fake range templates
  public static CallTarget create(FrameDescriptor frameDescriptor, FrameDescriptor scopeDescriptor, StatementNode body) {
    return new TemplatesRootNode(null, frameDescriptor, scopeDescriptor, body).getCallTarget();
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

  public static class CopyScopeNode extends StatementNode {

    @Override
    public void executeVoid(VirtualFrame frame) {
      frame.setObjectStatic(SCOPE_SLOT, frame.getArguments()[DEFINING_SCOPE_ARG]);
    }

    public static CopyScopeNode create() {
      return new CopyScopeNode();
    }
  }

  public static class CreateScopeNode extends StatementNode {

    private final FrameDescriptor scopeDescriptor;

    private CreateScopeNode(FrameDescriptor scopeDescriptor) {
      this.scopeDescriptor = scopeDescriptor;
    }

    public static CreateScopeNode create(FrameDescriptor scopeDescriptor) {
      return new CreateScopeNode(scopeDescriptor);
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
      MaterializedFrame scope = Truffle.getRuntime().createMaterializedFrame(frame.getArguments(), scopeDescriptor);
      frame.setObjectStatic(SCOPE_SLOT, scope);
    }
  }
}
