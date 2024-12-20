package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.source.SourceSection;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.TypeError;
import tailspin.language.nodes.TestUtil;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.nodes.value.WriteContextValueNode;

public class TransformTest {
  SourceSection sourceSection = INTERNAL_CODE_SOURCE;

  @Test
  void adds_current_value() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode valueNode = AddNode.create(
        IntegerLiteral.create(12, sourceSection),
        ReadContextValueNode.create(-1, cvSlot), false, sourceSection);
    assertEquals(46L,
        TestUtil.evaluate(valueNode,
            fdb.build(),
            List.of(WriteContextValueNode.create(-1, cvSlot, IntegerLiteral.create(34L,
                sourceSection)))));
  }

  @Test
  void reference_null_current_value_blows_up() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode valueNode  = AddNode.create(
        IntegerLiteral.create(12, sourceSection),
        ReadContextValueNode.create(-1, cvSlot), false, sourceSection);
    assertThrows(TypeError.class, () -> TestUtil.evaluate(valueNode, fdb.build(), List.of()));
  }
}
