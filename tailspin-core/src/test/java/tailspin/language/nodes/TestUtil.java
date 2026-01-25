package tailspin.language.nodes;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.List;

public class TestUtil {
  public static Object evaluate(ValueNode node) {
    return evaluate(node, FrameDescriptor.newBuilder().build(), List.of());
  }

  public static Object evaluate(ValueNode node, FrameDescriptor fd, List<StatementNode> definitions) {
    var rootNode = new TestRootNode(fd, definitions, node);
    CallTarget callTarget = rootNode.getCallTarget();

    return callTarget.call();
  }

  public static final class TestRootNode extends RootNode {
    @SuppressWarnings("FieldMayBeFinal")
    @Children
    private StatementNode[] definitions;
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private ValueNode node;

    public TestRootNode(FrameDescriptor fd, List<StatementNode> definitions, ValueNode node) {
      super(null, fd);
      this.definitions = definitions.toArray(new StatementNode[0]);
      this.node = node;
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frame) {
      for (StatementNode definition : definitions) {
        definition.executeVoid(frame);
      }
      return node.executeGeneric(frame);
    }
  }
}
