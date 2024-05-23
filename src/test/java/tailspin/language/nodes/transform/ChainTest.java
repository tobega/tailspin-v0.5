package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.interop.StopIterationException;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.TestUtil;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.array.ArrayLiteral;
import tailspin.language.nodes.array.ArrayReadNode;
import tailspin.language.nodes.iterate.ChainNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.iterate.RangeIteration;
import tailspin.language.nodes.value.LocalReferenceNode;
import tailspin.language.runtime.ResultIterator;

public class ChainTest {
  @Test
  void expression_chain_stage() throws StopIterationException {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int rangeSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode expr = AddNode.create(
            IntegerLiteral.create(12),
            LocalReferenceNode.create(rangeSlot));
    RangeIteration source = RangeIteration.create(rangeSlot, expr, resultSlot, IntegerLiteral.create(1L), IntegerLiteral.create(3L), IntegerLiteral.create(1L));
    ResultIterator result = (ResultIterator) TestUtil.evaluate(source, fdb.build(),
        List.of());
    assertEquals(13L, result.getIteratorNextElement());
    assertEquals(14L, result.getIteratorNextElement());
    assertEquals(15L, result.getIteratorNextElement());
    assertFalse(result.hasIteratorNextElement());
  }

  @Test
  void expression_chain_stage_single_value() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int valuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int isFirstSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode expr = AddNode.create(
            IntegerLiteral.create(12),
            LocalReferenceNode.create(cvSlot));
    ValueNode source = IntegerLiteral.create(1L);
    ChainNode chain = ChainNode.create(valuesSlot, cvSlot, resultSlot, List.of(source, expr), isFirstSlot);
    assertEquals(13L, TestUtil.evaluate(chain, fdb.build(),
        List.of()));
  }

  @Test
  void expression_chain_stage_single_array() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int valuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int isFirstSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode source = ArrayLiteral.create(List.of(IntegerLiteral.create(12)));
    ValueNode expr = ArrayReadNode.create(LocalReferenceNode.create(cvSlot), IntegerLiteral.create(1));
    ChainNode chain = ChainNode.create(valuesSlot, cvSlot, resultSlot, List.of(source, expr), isFirstSlot);
    assertEquals(12L, TestUtil.evaluate(chain, fdb.build(),
        List.of()));
  }
}
