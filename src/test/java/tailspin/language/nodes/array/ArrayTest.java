package tailspin.language.nodes.array;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.TestUtil;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.numeric.RangeLiteral;
import tailspin.language.nodes.processor.MessageNode;
import tailspin.language.nodes.transform.ChainNode;
import tailspin.language.runtime.TailspinArray;

public class ArrayTest {
  @Test
  void reads_array_value() {
    ValueNode array = ArrayLiteral.create(
        List.of(
            RangeLiteral.create(IntegerLiteral.create(1L), IntegerLiteral.create(3L), IntegerLiteral.create(1L)),
            IntegerLiteral.create(6),
            RangeLiteral.create(IntegerLiteral.create(10L), IntegerLiteral.create(15L), IntegerLiteral.create(5L))));
    ValueNode readNode = ArrayReadNode.create(array, IntegerLiteral.create(4));
    assertEquals(6L, TestUtil.evaluate(readNode));
  }

  @Test
  void write_array_value() {
    ValueNode array = ArrayLiteral.create(
        List.of(
            RangeLiteral.create(IntegerLiteral.create(1L), IntegerLiteral.create(3L), IntegerLiteral.create(1L)),
            IntegerLiteral.create(6),
            RangeLiteral.create(IntegerLiteral.create(10L), IntegerLiteral.create(15L), IntegerLiteral.create(5L))));
    ValueNode writeNode = ArrayWriteNode.create(array, IntegerLiteral.create(4), IntegerLiteral.create(35));
    ValueNode readNode = ArrayReadNode.create(writeNode, IntegerLiteral.create(4));
    assertEquals(35L, TestUtil.evaluate(readNode));
  }

  @Test
  void get_array_length() {
    ValueNode array = ArrayLiteral.create(
        List.of(
            RangeLiteral.create(IntegerLiteral.create(1L), IntegerLiteral.create(3L), IntegerLiteral.create(1L)),
            IntegerLiteral.create(6),
            RangeLiteral.create(IntegerLiteral.create(10L), IntegerLiteral.create(15L), IntegerLiteral.create(5L))));
    ValueNode lengthNode = MessageNode.create("length", array);
    assertEquals(6L, TestUtil.evaluate(lengthNode));
  }

  @Test
  void create_zeroes() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int valuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    ValueNode contents = ChainNode.create(valuesSlot, cvSlot, resultSlot,
        List.of(RangeLiteral.create(IntegerLiteral.create(1L), IntegerLiteral.create(3L), IntegerLiteral.create(1L)),
            IntegerLiteral.create(0)
        ));
    ValueNode arrayNode = ArrayLiteral.create(List.of(contents));
    TailspinArray array = (TailspinArray) TestUtil.evaluate(arrayNode, fdb.build(), List.of());
    assertEquals(3, array.getArraySize());
    for (int i = 0; i < array.getArraySize(); i++) assertEquals(0L, array.getNative(i));
  }
}
