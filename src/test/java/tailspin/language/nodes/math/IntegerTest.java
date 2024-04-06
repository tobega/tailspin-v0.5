package tailspin.language.nodes.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tailspin.language.TestUtil;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.math.BigIntegerLiteral;
import tailspin.language.nodes.value.math.IntegerLiteral;

public class IntegerTest {
  @Test
  public void adds_12_and_34_correctly() {
    ValueNode exprNode = AddNodeGen.create(
        new IntegerLiteral(12),
        new IntegerLiteral(34));
    assertEquals(46L, TestUtil.evaluate(exprNode).next());
  }

  @Test
  public void adding_1_to_int_max_does_not_overflow() {
    ValueNode exprNode = AddNodeGen.create(
        new IntegerLiteral(Long.MAX_VALUE),
        new IntegerLiteral(1));
    assertEquals(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE), TestUtil.evaluate(exprNode).next());
  }

  @Test
  public void adding_long_to_BigInteger_works() {
    BigInteger rndBig = new BigInteger(100, new Random());
    ValueNode exprNode = AddNodeGen.create(
        new BigIntegerLiteral(rndBig),
        new IntegerLiteral(5)
    );
    assertEquals(rndBig.add(BigInteger.valueOf(5)), TestUtil.evaluate(exprNode).next());
  }
}
