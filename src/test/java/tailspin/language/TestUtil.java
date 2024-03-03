package tailspin.language;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.Iterator;
import java.util.List;
import tailspin.language.nodes.ExpressionNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.transform.ExpressionTransformNode;

public class TestUtil {
  public static Object evaluate(ExpressionNode node) {
    return evaluate(node, FrameDescriptor.newBuilder().build(), List.of());
  }

  public static Object evaluate(ExpressionNode node, FrameDescriptor fd, List<StatementNode> definitions) {
    var rootNode = new TestRootNode(fd, definitions, new ExpressionTransformNode(node));
    CallTarget callTarget = rootNode.getCallTarget();

    @SuppressWarnings("unchecked")
    Iterator<Object> results = (Iterator<Object>) callTarget.call();
    return results.next();
  }

  public static Object evaluate(TransformNode node, FrameDescriptor fd, List<StatementNode> definitions) {
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
    private TransformNode transformNode;

    public TestRootNode(FrameDescriptor fd, List<StatementNode> definitions, TransformNode transformNode) {
      super(null, fd);
      this.definitions = definitions.toArray(new StatementNode[definitions.size()]);
      this.transformNode = transformNode;
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frame) {
      for (StatementNode definition : definitions) {
        definition.executeVoid(frame);
      }
      return this.transformNode.execute(frame);
    }
  }

  public static class TestSource extends ExpressionNode {
    private final Object value;

    public TestSource(Object value) {
      this.value = value;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
      return value;
    }
  }
}
