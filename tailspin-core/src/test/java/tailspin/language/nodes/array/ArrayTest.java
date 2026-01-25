package tailspin.language.nodes.array;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.source.SourceSection;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.nodes.TestUtil;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.RangeIteration;
import tailspin.language.nodes.iterate.ResultAggregatingNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.processor.MessageNode;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.runtime.TailspinArray;

public class ArrayTest {
  SourceSection sourceSection = INTERNAL_CODE_SOURCE;
  @Test
  void reads_array_value() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int rangeSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int startSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int endSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int incrementSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode array = ArrayLiteral.create(buildSlot,
        List.of(
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)
                ), startSlot, IntegerLiteral.create(1L, sourceSection),
                true, endSlot, IntegerLiteral.create(3L, sourceSection), true, incrementSlot, IntegerLiteral.create(1L,
                    sourceSection), sourceSection),
            ResultAggregatingNode.create(IntegerLiteral.create(6, sourceSection)),
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)
                ), startSlot, IntegerLiteral.create(10L, sourceSection),
                true, endSlot, IntegerLiteral.create(15L, sourceSection), true, incrementSlot, IntegerLiteral.create(5L,
                    sourceSection), sourceSection)),
        sourceSection);
    ValueNode readNode = ArrayReadNode.create(false, array, IntegerLiteral.create(4, sourceSection), null,
        sourceSection);
    assertEquals(6L, TestUtil.evaluate(readNode, fdb.build(), List.of()));
  }

  @Test
  void write_array_value() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int rangeSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int startSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int endSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int incrementSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode array = ArrayLiteral.create(buildSlot,
        List.of(
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)
                ), startSlot, IntegerLiteral.create(1L, sourceSection),
                true, endSlot, IntegerLiteral.create(3L, sourceSection), true, incrementSlot, IntegerLiteral.create(1L,
                    sourceSection), sourceSection),
            ResultAggregatingNode.create(IntegerLiteral.create(6, sourceSection)),
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)
                ), startSlot, IntegerLiteral.create(10L, sourceSection),
                true, endSlot, IntegerLiteral.create(15L, sourceSection), true, incrementSlot, IntegerLiteral.create(5L,
                    sourceSection), sourceSection)),
        sourceSection);
    ValueNode writeNode = ArrayMutateNode.create(array, IntegerLiteral.create(4, sourceSection), IntegerLiteral.create(35,
        sourceSection), sourceSection);
    ValueNode readNode = ArrayReadNode.create(false, writeNode, IntegerLiteral.create(4,
        sourceSection), null, sourceSection);
    assertEquals(35L, TestUtil.evaluate(readNode, fdb.build(), List.of()));
  }

  @Test
  void get_array_length() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int rangeSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int startSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int endSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int incrementSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode array = ArrayLiteral.create(buildSlot,
        List.of(
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)
                ), startSlot, IntegerLiteral.create(1L, sourceSection),
                true, endSlot, IntegerLiteral.create(3L, sourceSection), true, incrementSlot, IntegerLiteral.create(1L,
                    sourceSection), sourceSection),
            ResultAggregatingNode.create(IntegerLiteral.create(6, sourceSection)),
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)
                ), startSlot, IntegerLiteral.create(10L, sourceSection),
                true, endSlot, IntegerLiteral.create(15L, sourceSection), true, incrementSlot, IntegerLiteral.create(5L,
                    sourceSection), sourceSection)),
        sourceSection);
    ValueNode lengthNode = MessageNode.create("length", array, sourceSection);
    assertEquals(6L, TestUtil.evaluate(lengthNode, fdb.build(), List.of()));
  }

  @Test
  void create_zeroes() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int rangeSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int startSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int endSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int incrementSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    TransformNode contents = RangeIteration.create(rangeSlot, ResultAggregatingNode.create(IntegerLiteral.create(0,
                sourceSection)
        ), startSlot, IntegerLiteral.create(1L, sourceSection),
        true, endSlot, IntegerLiteral.create(3L,
            sourceSection), true, incrementSlot, IntegerLiteral.create(1L, sourceSection),
        sourceSection);
    ValueNode arrayNode = ArrayLiteral.create(buildSlot, List.of(contents), sourceSection);
    TailspinArray array = (TailspinArray) TestUtil.evaluate(arrayNode, fdb.build(), List.of());
    assertEquals(3, array.getArraySize());
    for (int i = 0; i < array.getArraySize(); i++) assertEquals(0L, array.getNative(i, false));
  }
}
