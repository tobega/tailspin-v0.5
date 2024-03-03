package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.List;
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
        LocalReferenceNodeGen.create(cvSlot));
    assertEquals(46L,
        TestUtil.evaluate(exprNode,
            fdb.build(),
            List.of(LocalDefinitionNodeGen.create(new IntegerLiteral(34L), cvSlot))));
  }

  @Test
  void reference_null_current_value_blows_up() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, "", null);
    ExpressionNode exprNode = AddNodeGen.create(
        new IntegerLiteral(12),
        LocalReferenceNodeGen.create(cvSlot));
    assertThrows(TypeError.class, () -> TestUtil.evaluate(exprNode, fdb.build(), List.of()));
  }
}
