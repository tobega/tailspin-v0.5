package tailspin.language.nodes;

import static tailspin.language.runtime.Templates.EMIT_SLOT;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import tailspin.language.TailspinLanguage;

public class ProgramRootNode extends RootNode {

  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private StatementNode statement;
  private ProgramRootNode(TruffleLanguage<?> language,
      FrameDescriptor frameDescriptor, StatementNode statement) {
    super(language, frameDescriptor);
    this.statement = statement;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    statement.executeVoid(frame);
    Object results = frame.getObjectStatic(EMIT_SLOT);
    frame.setObjectStatic(EMIT_SLOT, null);
    return 3L;
  }

  public static CallTarget create(TailspinLanguage language, FrameDescriptor fd, StatementNode body) {
    return new ProgramRootNode(language, fd, body).getCallTarget();
  }
}
