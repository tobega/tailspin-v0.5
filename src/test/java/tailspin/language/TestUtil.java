package tailspin.language;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import tailspin.language.nodes.TailspinNode;

public class TestUtil {
  public static Object evaluate(TailspinNode node) {
    var rootNode = new TestRootNode(FrameDescriptor.newBuilder().build(), -1, null, node);
    CallTarget callTarget = rootNode.getCallTarget();

    return callTarget.call();
  }

  public static Object evaluate(TailspinNode node, FrameDescriptor fd, int cvSlot, Object currentValue) {
    var rootNode = new TestRootNode(fd, cvSlot, currentValue, node);
    CallTarget callTarget = rootNode.getCallTarget();

    return callTarget.call();
  }

  public static final class TestRootNode extends RootNode {

    private final int cvSlot;
    private final Object currentValue;
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private TailspinNode exprNode;

    public TestRootNode(FrameDescriptor fd, int cvSlot, Object currentValue, TailspinNode exprNode) {
      super(null, fd);
      this.cvSlot = cvSlot;
      this.currentValue = currentValue;
      this.exprNode = exprNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
      if (currentValue != null) {
        if (currentValue instanceof Long lv) {
          frame.getFrameDescriptor().setSlotKind(cvSlot, FrameSlotKind.Long);
          frame.setLong(cvSlot, lv);
        } else {
          frame.getFrameDescriptor().setSlotKind(cvSlot, FrameSlotKind.Object);
          frame.setObject(cvSlot, currentValue);
        }
      }
      return this.exprNode.executeGeneric(frame);
    }
  }
}
