package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.TestUtil;
import tailspin.language.TestUtil.TestSource;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.math.AddNodeGen;
import tailspin.language.nodes.value.GetNextStreamValueNode;
import tailspin.language.nodes.value.ValueTransformNode;
import tailspin.language.nodes.value.math.IntegerLiteral;

public class ChainTest {
  @Test
  void expression_chain_stage() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int valuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    StatementNode setCurrentValue = LocalDefinitionNodeGen.create(
        new GetNextStreamValueNode(valuesSlot),
        cvSlot
    );
    ValueNode expr = AddNodeGen.create(
            new IntegerLiteral(12),
            LocalReferenceNodeGen.create(cvSlot));
    ChainStageNode chainStage = new ChainStageNode(setCurrentValue, new ValueTransformNode(expr), resultSlot);
    TestSource source = new TestSource(List.of(1L, 2L, 3L).iterator());
    ChainNode chain = new ChainNode(valuesSlot, List.of(source, chainStage));
    Iterator<Object> result = TestUtil.evaluate(chain, fdb.build(),
        List.of());
    assertEquals(13L, result.next());
    assertEquals(14L, result.next());
    assertEquals(15L, result.next());
    assertFalse(result.hasNext());
  }

}
