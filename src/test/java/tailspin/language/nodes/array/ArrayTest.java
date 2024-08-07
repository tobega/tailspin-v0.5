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
    int rangeSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode array = ArrayLiteral.create(buildSlot,
        List.of(
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)), IntegerLiteral.create(1L),
                true, IntegerLiteral.create(3L), IntegerLiteral.create(1L), true),
            ResultAggregatingNode.create(IntegerLiteral.create(6)),
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)), IntegerLiteral.create(10L),
                true, IntegerLiteral.create(15L), IntegerLiteral.create(5L), true)));
    ValueNode readNode = ArrayReadNode.create(array, IntegerLiteral.create(4));
    assertEquals(6L, TestUtil.evaluate(readNode, fdb.build(), List.of()));
  }

  @Test
  void write_array_value() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int rangeSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode array = ArrayLiteral.create(buildSlot,
        List.of(
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)), IntegerLiteral.create(1L),
                true, IntegerLiteral.create(3L), IntegerLiteral.create(1L), true),
            ResultAggregatingNode.create(IntegerLiteral.create(6)),
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)), IntegerLiteral.create(10L),
                true, IntegerLiteral.create(15L), IntegerLiteral.create(5L), true)));
    ValueNode writeNode = ArrayWriteNode.create(array, IntegerLiteral.create(4), IntegerLiteral.create(35));
    ValueNode readNode = ArrayReadNode.create(writeNode, IntegerLiteral.create(4));
    assertEquals(35L, TestUtil.evaluate(readNode, fdb.build(), List.of()));
  }

  @Test
  void get_array_length() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int rangeSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode array = ArrayLiteral.create(buildSlot,
        List.of(
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)), IntegerLiteral.create(1L),
                true, IntegerLiteral.create(3L), IntegerLiteral.create(1L), true),
            ResultAggregatingNode.create(IntegerLiteral.create(6)),
            RangeIteration.create(rangeSlot, ResultAggregatingNode.create(ReadContextValueNode.create(-1, rangeSlot)), IntegerLiteral.create(10L),
                true, IntegerLiteral.create(15L), IntegerLiteral.create(5L), true)));
    ValueNode lengthNode = MessageNode.create("length", array);
    assertEquals(6L, TestUtil.evaluate(lengthNode, fdb.build(), List.of()));
  }

  @Test
  void create_zeroes() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int rangeSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    TransformNode contents = RangeIteration.create(rangeSlot, ResultAggregatingNode.create(IntegerLiteral.create(0)), IntegerLiteral.create(1L),
        true, IntegerLiteral.create(3L), IntegerLiteral.create(1L), true);
    ValueNode arrayNode = ArrayLiteral.create(buildSlot, List.of(contents));
    TailspinArray array = (TailspinArray) TestUtil.evaluate(arrayNode, fdb.build(), List.of());
    assertEquals(3, array.getArraySize());
    for (int i = 0; i < array.getArraySize(); i++) assertEquals(0L, array.getNative(i));
  }
}
