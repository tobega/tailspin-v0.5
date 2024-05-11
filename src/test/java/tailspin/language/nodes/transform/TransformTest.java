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
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.value.LocalDefinitionNodeGen;
import tailspin.language.nodes.value.LocalReferenceNode;

public class TransformTest {

  @Test
  void adds_current_value() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    ValueNode valueNode = AddNode.create(
        IntegerLiteral.create(12),
        LocalReferenceNode.create(cvSlot));
    assertEquals(46L,
        TestUtil.evaluate(valueNode,
            fdb.build(),
            List.of(LocalDefinitionNodeGen.create(IntegerLiteral.create(34L), cvSlot))));
  }

  @Test
  void reference_null_current_value_blows_up() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    ValueNode valueNode  = AddNode.create(
        IntegerLiteral.create(12),
        LocalReferenceNode.create(cvSlot));
    assertThrows(TypeError.class, () -> TestUtil.evaluate(valueNode, fdb.build(), List.of()));
  }
}
