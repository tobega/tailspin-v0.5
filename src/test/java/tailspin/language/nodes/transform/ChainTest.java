package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.nodes.TestUtil;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.array.ArrayLiteral;
import tailspin.language.nodes.array.ArrayReadNode;
import tailspin.language.nodes.iterate.ChainNode;
import tailspin.language.nodes.iterate.RangeIteration;
import tailspin.language.nodes.iterate.ResultAggregatingNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.nodes.value.SingleValueNode;
import tailspin.language.nodes.value.TransformResultNode;
import tailspin.language.runtime.Templates;

public class ChainTest {
  @Test
  void expression_chain_stage() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int rangeSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode expr = AddNode.create(
            IntegerLiteral.create(12),
            ReadContextValueNode.create(-1, rangeSlot));
    RangeIteration source = RangeIteration.create(rangeSlot, ResultAggregatingNode.create(expr), IntegerLiteral.create(1L), IntegerLiteral.create(3L), IntegerLiteral.create(1L));
    source.setResultSlot(resultSlot);
    @SuppressWarnings("unchecked")
    Iterator<Object> result = ((ArrayList<Object>) TestUtil.evaluate(new TransformResultNode(source), fdb.build(),
        List.of())).iterator();
    assertEquals(13L, result.next());
    assertEquals(14L, result.next());
    assertEquals(15L, result.next());
    assertFalse(result.hasNext());
  }

  @Test
  void expression_chain_stage_single_value() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int valuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode expr = AddNode.create(
            IntegerLiteral.create(12),
            ReadContextValueNode.create(-1, cvSlot));
    ValueNode source = IntegerLiteral.create(1L);
    ChainNode chain = ChainNode.create(valuesSlot, cvSlot, resultSlot, List.of(
        ResultAggregatingNode.create(source), ResultAggregatingNode.create(expr)));
    assertEquals(13L, TestUtil.evaluate(SingleValueNode.create(chain), fdb.build(), List.of()));
  }

  @Test
  void expression_chain_stage_single_array() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int valuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode source = ArrayLiteral.create(buildSlot, List.of(ResultAggregatingNode.create(IntegerLiteral.create(12))));
    ValueNode expr = ArrayReadNode.create(ReadContextValueNode.create(-1, cvSlot), IntegerLiteral.create(1));
    ChainNode chain = ChainNode.create(valuesSlot, cvSlot, resultSlot, List.of(
        ResultAggregatingNode.create(source), ResultAggregatingNode.create(expr)));
    assertEquals(12L, TestUtil.evaluate(SingleValueNode.create(chain), fdb.build(),
        List.of()));
  }
}
