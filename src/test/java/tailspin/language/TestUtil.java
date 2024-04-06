package tailspin.language;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.Iterator;
import java.util.List;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.ValueTransformNode;

public class TestUtil {
  public static Iterator<Object> evaluate(ValueNode node) {
    return evaluate(new ValueTransformNode(node), FrameDescriptor.newBuilder().build(), List.of());
  }

  public static Iterator<Object> evaluate(TransformNode node, FrameDescriptor fd, List<StatementNode> definitions) {
    var rootNode = new TestRootNode(fd, definitions, node);
    CallTarget callTarget = rootNode.getCallTarget();

    @SuppressWarnings("unchecked")
    Iterator<Object> result = (Iterator<Object>) callTarget.call();
    return result;
  }

  public static final class TestRootNode extends RootNode {
    @SuppressWarnings("FieldMayBeFinal")
    @Children
    private StatementNode[] definitions;
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private TransformNode node;

    public TestRootNode(FrameDescriptor fd, List<StatementNode> definitions, TransformNode node) {
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
      return node.executeTransform(frame);
    }
  }

  public static class TestSource extends TransformNode {
    private final Iterator<?> values;

    public TestSource(Iterator<?> values) {
      this.values = values;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<Object> executeTransform(VirtualFrame frame) {
      return (Iterator<Object>) values;
    }
  }
}
