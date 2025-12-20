package tailspin.language.nodes;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;
import static tailspin.language.runtime.Templates.EMIT_SLOT;
import static tailspin.language.runtime.Templates.SCOPE_SLOT;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.util.ArrayList;
import tailspin.language.TailspinLanguage;
import tailspin.language.runtime.TailspinArray;

public class ProgramRootNode extends RootNode {

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private StatementNode createScope;

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private StatementNode statement;

  private ProgramRootNode(TruffleLanguage<?> language,
      FrameDescriptor frameDescriptor, FrameDescriptor scopeDescriptor, StatementNode statement) {
    super(language, frameDescriptor);
    this.createScope = CreateProgramScopeNode.create(scopeDescriptor, INTERNAL_CODE_SOURCE);
    this.statement = statement;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    createScope.executeVoid(frame);
    statement.executeVoid(frame);
    Object results = frame.getObjectStatic(EMIT_SLOT);
    frame.setObjectStatic(EMIT_SLOT, null);
    if (results instanceof ArrayList<?> arrayList) {
      // Wrap in a double array for now
      results = TailspinArray.value(new Object[] {TailspinArray.value(arrayList.toArray())});
    }
    return results;
  }

  public static CallTarget create(TailspinLanguage language, FrameDescriptor fd, FrameDescriptor scopeDescriptor, StatementNode body) {
    return new ProgramRootNode(language, fd, scopeDescriptor, body).getCallTarget();
  }

  public static class CreateProgramScopeNode extends StatementNode {

    private static final Object[] EMPTY_ARGS = new Object[0];

    private final FrameDescriptor scopeDescriptor;

    private CreateProgramScopeNode(FrameDescriptor scopeDescriptor, SourceSection sourceSection) {
      super(sourceSection);
      this.scopeDescriptor = scopeDescriptor;
    }

    public static CreateProgramScopeNode create(FrameDescriptor scopeDescriptor,
        SourceSection sourceSection) {
      return new CreateProgramScopeNode(scopeDescriptor, sourceSection);
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
      MaterializedFrame scope = Truffle.getRuntime().createMaterializedFrame(EMPTY_ARGS, scopeDescriptor);
      frame.setObjectStatic(SCOPE_SLOT, scope);
    }
  }
}
