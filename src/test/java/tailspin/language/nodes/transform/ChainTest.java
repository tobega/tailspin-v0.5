package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.TestUtil;
import tailspin.language.TestUtil.TestSource;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.GetNextStreamValueNode;
import tailspin.language.nodes.value.LocalDefinitionNodeGen;
import tailspin.language.nodes.value.LocalReferenceNodeGen;
import tailspin.language.nodes.value.math.AddNodeGen;
import tailspin.language.nodes.value.math.IntegerLiteral;
import tailspin.language.runtime.ResultIterator;

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
    TestSource source = new TestSource(new Object[]{1L, 2L, 3L});
    ChainNode chain = new ChainNode(valuesSlot, List.of(source, chainStage));
    ResultIterator result = (ResultIterator) TestUtil.evaluate(chain, fdb.build(),
        List.of());
    assertEquals(13L, result.getIteratorNextElement());
    assertEquals(14L, result.getIteratorNextElement());
    assertEquals(15L, result.getIteratorNextElement());
    assertFalse(result.hasIteratorNextElement());
  }

  @Test
  void expression_chain_stage_single_values() {
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
    TransformNode source = new ValueTransformNode(new IntegerLiteral(1L));
    ChainNode chain = new ChainNode(valuesSlot, List.of(source, chainStage));
    assertEquals(13L, TestUtil.evaluate(chain, fdb.build(),
        List.of()));
  }
}
