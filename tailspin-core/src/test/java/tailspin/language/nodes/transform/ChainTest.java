package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.source.SourceSection;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.nodes.TestUtil;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.array.ArrayLiteral;
import tailspin.language.nodes.array.ArrayReadNode;
import tailspin.language.nodes.iterate.ChainNode;
import tailspin.language.nodes.iterate.ResultAggregatingNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.nodes.value.SingleValueNode;
import tailspin.language.runtime.Templates;

public class ChainTest {
  SourceSection sourceSection = INTERNAL_CODE_SOURCE;

  @Test
  void expression_chain_stage_single_value() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int cvSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int valuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode expr = AddNode.create(
            IntegerLiteral.create(12, sourceSection),
            ReadContextValueNode.create(-1, cvSlot), false, sourceSection);
    ValueNode source = IntegerLiteral.create(1L, sourceSection);
    ChainNode chain = ChainNode.create(valuesSlot, cvSlot, resultSlot, List.of(
        ResultAggregatingNode.create(source), ResultAggregatingNode.create(expr
        )));
    assertEquals(13L, TestUtil.evaluate(SingleValueNode.create(chain), fdb.build(), List.of()));
  }

  @Test
  void expression_chain_stage_single_array() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int cvSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int valuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode source = ArrayLiteral.create(buildSlot, List.of(ResultAggregatingNode.create(IntegerLiteral.create(12,
            sourceSection)
    )), sourceSection);
    ValueNode expr = ArrayReadNode.create(false, ReadContextValueNode.create(-1, cvSlot), IntegerLiteral.create(1,
        sourceSection), null, sourceSection);
    ChainNode chain = ChainNode.create(valuesSlot, cvSlot, resultSlot, List.of(
        ResultAggregatingNode.create(source), ResultAggregatingNode.create(expr
        )));
    assertEquals(12L, TestUtil.evaluate(SingleValueNode.create(chain), fdb.build(),
        List.of()));
  }
}
