package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.TestUtil;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.literals.IntegerLiteral;
import tailspin.language.nodes.math.AddNodeGen;

public class ChainTest {
  @Test
  void expression_chain_stage() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, "", null);
    int valuesSlot = fdb.addSlot(FrameSlotKind.Illegal, "", null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Illegal, "", null);
    StatementNode setValues = LocalDefinitionNodeGen.create(
        new TestUtil.TestSource(List.of(1L,2L,3L).iterator()),
        valuesSlot
    );
    StatementNode setCurrentValue = LocalDefinitionNodeGen.create(
        new GetNextStreamValueNode(valuesSlot),
        cvSlot
    );
    ExpressionTransformNode exprTr = new ExpressionTransformNode(
        AddNodeGen.create(
            new IntegerLiteral(12),
            LocalReferenceNodeGen.create(cvSlot))
    );
    ChainStageNode chainStage = new ChainStageNode(setCurrentValue, exprTr, resultSlot);
    @SuppressWarnings("unchecked")
    Iterator<Object> result = (Iterator<Object>) TestUtil.evaluate(chainStage, fdb.build(),
        List.of(setValues));
    assertEquals(13L, result.next());
    assertEquals(14L, result.next());
    assertEquals(15L, result.next());
    assertFalse(result.hasNext());
  }

}
