package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import tailspin.language.TestUtil;
import tailspin.language.TypeError;
import tailspin.language.nodes.ExpressionNode;
import tailspin.language.nodes.literals.IntegerLiteral;
import tailspin.language.nodes.math.AddNodeGen;

public class TransformTest {

  @Test
  void adds_current_value() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, "", null);
    ExpressionNode exprNode = AddNodeGen.create(
        new IntegerLiteral(12),
        CurrentValueReferenceNodeGen.create(cvSlot));
    assertEquals(46L, TestUtil.evaluate(exprNode, fdb.build(), cvSlot, 34L));
  }

  @Test
  void reference_null_current_value_blows_up() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, "", null);
    ExpressionNode exprNode = AddNodeGen.create(
        new IntegerLiteral(12),
        CurrentValueReferenceNodeGen.create(cvSlot));
    assertThrows(TypeError.class, () -> TestUtil.evaluate(exprNode, fdb.build(), cvSlot, null));
  }

  @Test
  void expression_transform() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, "", null);
    ExpressionTransformNode exprTr = new ExpressionTransformNode(
        AddNodeGen.create(
            new IntegerLiteral(12),
            CurrentValueReferenceNodeGen.create(cvSlot))
    );
    @SuppressWarnings("unchecked")
    Iterator<Object> result = (Iterator<Object>) TestUtil.evaluate(exprTr, fdb.build(), cvSlot, 23L);
    assertEquals(35L, result.next());
    assertFalse(result.hasNext());
  }
}
