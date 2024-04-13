package tailspin.language.nodes.value;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.transform.ReadCvArgumentNode;

public class ValueTemplatesRootNode extends RootNode {
  private final StatementNode setCurrentValue;
  private final StatementNode statement;
  private final ValueNode result;
  private ValueTemplatesRootNode(TruffleLanguage<?> language,
      FrameDescriptor frameDescriptor, StatementNode setCurrentValue, StatementNode statement, int resultSlot) {
    super(language, frameDescriptor);
    this.setCurrentValue = setCurrentValue;
    this.statement = statement;
    this.result = LocalReferenceNodeGen.create(resultSlot);
  }

  @Override
  public Object execute(VirtualFrame frame) {
    setCurrentValue.executeVoid(frame);
    statement.executeVoid(frame);
    return result.executeGeneric(frame);
  }

  public static CallTarget create(FrameDescriptor fd, int cvSlot, StatementNode body, int resultSlot) {
    return new ValueTemplatesRootNode(null, fd,
        LocalDefinitionNodeGen.create(new ReadCvArgumentNode(), cvSlot), body, resultSlot
    ).getCallTarget();
  }
}
