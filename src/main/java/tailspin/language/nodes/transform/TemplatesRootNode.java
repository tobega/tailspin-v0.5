package tailspin.language.nodes.transform;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.ArrayDeque;
import java.util.Queue;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.value.LocalDefinitionNodeGen;
import tailspin.language.runtime.DeepIterator;

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
    Queue<Object> results = new ArrayDeque<>();
    frame.setObjectStatic(resultSlot, results);
    setCurrentValue.executeVoid(frame);
    statement.executeVoid(frame);
    frame.clearObjectStatic(resultSlot);
    return new DeepIterator(results.iterator());
  }

  public static CallTarget create(FrameDescriptor fd, int cvSlot, StatementNode body, int resultSlot) {
    return new TemplatesRootNode(null, fd,
        LocalDefinitionNodeGen.create(new ReadCvArgumentNode(), cvSlot), body, resultSlot
    ).getCallTarget();
  }
}
