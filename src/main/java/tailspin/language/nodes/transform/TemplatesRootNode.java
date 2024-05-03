package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.RESULT_SLOT;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.value.LocalDefinitionNodeGen;

public class TemplatesRootNode extends RootNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private StatementNode setCurrentValue;
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private StatementNode statement;
  private TemplatesRootNode(TruffleLanguage<?> language,
      FrameDescriptor frameDescriptor, StatementNode setCurrentValue, StatementNode statement) {
    super(language, frameDescriptor);
    this.setCurrentValue = setCurrentValue;
    this.statement = statement;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    setCurrentValue.executeVoid(frame);
    statement.executeVoid(frame);
    Object results = frame.getObjectStatic(RESULT_SLOT);
    frame.setObjectStatic(RESULT_SLOT, null);
    return results;
  }

  public static CallTarget create(FrameDescriptor fd, StatementNode body) {
    return new TemplatesRootNode(null, fd,
        LocalDefinitionNodeGen.create(new ReadCvArgumentNode(), CV_SLOT), body).getCallTarget();
  }
}
