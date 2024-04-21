package tailspin.language.nodes.transform;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.value.LocalDefinitionNodeGen;

public class TemplatesRootNode extends RootNode {
  private final StatementNode setCurrentValue;
  private final StatementNode statement;
  private final int resultSlot;
  private TemplatesRootNode(TruffleLanguage<?> language,
      FrameDescriptor frameDescriptor, StatementNode setCurrentValue, StatementNode statement, int resultSlot) {
    super(language, frameDescriptor);
    this.setCurrentValue = setCurrentValue;
    this.statement = statement;
    this.resultSlot = resultSlot;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    setCurrentValue.executeVoid(frame);
    statement.executeVoid(frame);
    Object results = frame.getObjectStatic(resultSlot);
    frame.setObjectStatic(resultSlot, null);
    return results;
  }

  public static CallTarget create(FrameDescriptor fd, int cvSlot, StatementNode body, int resultSlot) {
    return new TemplatesRootNode(null, fd,
        LocalDefinitionNodeGen.create(new ReadCvArgumentNode(), cvSlot), body, resultSlot
    ).getCallTarget();
  }
}
