package tailspin.language.nodes.array;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.TestUtil;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.literals.IntegerLiteral;
import tailspin.language.nodes.literals.RangeLiteralNodeGen;

public class ArrayTest {
  @Test
  void reads_array_value() {
    ValueNode array = new ArrayLiteral(
        List.of(
            RangeLiteralNodeGen.create(new IntegerLiteral(1L), new IntegerLiteral(3L), new IntegerLiteral(1L)),
            new IntegerLiteral(6),
            RangeLiteralNodeGen.create(new IntegerLiteral(10L), new IntegerLiteral(15L), new IntegerLiteral(5L))));
    ValueNode readNode = ArrayReadNode.create(array, new IntegerLiteral(4));
    assertEquals(6L, TestUtil.evaluate(readNode));
  }

  @Test
  void write_array_value() {
    ValueNode array = new ArrayLiteral(
        List.of(
            RangeLiteralNodeGen.create(new IntegerLiteral(1L), new IntegerLiteral(3L), new IntegerLiteral(1L)),
            new IntegerLiteral(6),
            RangeLiteralNodeGen.create(new IntegerLiteral(10L), new IntegerLiteral(15L), new IntegerLiteral(5L))));
    ValueNode writeNode = ArrayWriteNode.create(array, new IntegerLiteral(4), new IntegerLiteral(35));
    ValueNode readNode = ArrayReadNode.create(writeNode, new IntegerLiteral(4));
    assertEquals(35L, TestUtil.evaluate(readNode));
  }
}
