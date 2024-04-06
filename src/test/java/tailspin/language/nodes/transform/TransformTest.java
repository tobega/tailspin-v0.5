package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.TestUtil;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.LocalDefinitionNodeGen;
import tailspin.language.nodes.value.LocalReferenceNodeGen;
import tailspin.language.nodes.value.ValueTransformNode;
import tailspin.language.nodes.value.math.AddNodeGen;
import tailspin.language.nodes.value.math.IntegerLiteral;

public class TransformTest {

  @Test
  void adds_current_value() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    ValueNode valueNode = AddNodeGen.create(
        new IntegerLiteral(12),
        LocalReferenceNodeGen.create(cvSlot));
    assertEquals(46L,
        TestUtil.evaluate(new ValueTransformNode(valueNode),
            fdb.build(),
            List.of(LocalDefinitionNodeGen.create(new IntegerLiteral(34L), cvSlot))).next());
  }

  @Test
  void reference_null_current_value_blows_up() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    ValueNode valueNode  = AddNodeGen.create(
        new IntegerLiteral(12),
        LocalReferenceNodeGen.create(cvSlot));
    assertThrows(TypeError.class, () -> TestUtil.evaluate(new ValueTransformNode(valueNode), fdb.build(), List.of()));
  }
}
