package tailspin.language.nodes.array;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.TestUtil;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.literals.IntegerLiteral;
import tailspin.language.nodes.literals.RangeLiteral;
import tailspin.language.nodes.processor.MessageNode;

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
}
