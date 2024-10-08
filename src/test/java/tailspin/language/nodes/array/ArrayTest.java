package tailspin.language.nodes.array;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
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
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)), startSlot, IntegerLiteral.create(1L),
                true, endSlot, IntegerLiteral.create(3L), true, incrementSlot, IntegerLiteral.create(1L)),
            ResultAggregatingNode.create(IntegerLiteral.create(6)),
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)), startSlot, IntegerLiteral.create(10L),
                true, endSlot, IntegerLiteral.create(15L), true, incrementSlot, IntegerLiteral.create(5L))));
    ValueNode readNode = ArrayReadNode.create(false, array, IntegerLiteral.create(4), null);
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
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)), startSlot, IntegerLiteral.create(1L),
                true, endSlot, IntegerLiteral.create(3L), true, incrementSlot, IntegerLiteral.create(1L)),
            ResultAggregatingNode.create(IntegerLiteral.create(6)),
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)), startSlot, IntegerLiteral.create(10L),
                true, endSlot, IntegerLiteral.create(15L), true, incrementSlot, IntegerLiteral.create(5L))));
    ValueNode writeNode = ArrayMutateNode.create(array, IntegerLiteral.create(4), IntegerLiteral.create(35));
    ValueNode readNode = ArrayReadNode.create(false, writeNode, IntegerLiteral.create(4), null);
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
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)), startSlot, IntegerLiteral.create(1L),
                true, endSlot, IntegerLiteral.create(3L), true, incrementSlot, IntegerLiteral.create(1L)),
            ResultAggregatingNode.create(IntegerLiteral.create(6)),
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)), startSlot, IntegerLiteral.create(10L),
                true, endSlot, IntegerLiteral.create(15L), true, incrementSlot, IntegerLiteral.create(5L))));
    ValueNode lengthNode = MessageNode.create("length", array);
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
    TransformNode contents = RangeIteration.create(rangeSlot, ResultAggregatingNode.create(IntegerLiteral.create(0)), startSlot, IntegerLiteral.create(1L),
        true, endSlot, IntegerLiteral.create(3L), true, incrementSlot, IntegerLiteral.create(1L));
    ValueNode arrayNode = ArrayLiteral.create(buildSlot, List.of(contents));
    TailspinArray array = (TailspinArray) TestUtil.evaluate(arrayNode, fdb.build(), List.of());
    assertEquals(3, array.getArraySize());
    for (int i = 0; i < array.getArraySize(); i++) assertEquals(0L, array.getNative(i, false));
  }
}
