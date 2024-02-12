package tailspin.language.nodes.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tailspin.language.TestUtil;
import tailspin.language.nodes.ExpressionNode;
import tailspin.language.nodes.literals.BigIntegerLiteral;
import tailspin.language.nodes.literals.IntegerLiteral;

public class IntegerTest {
  @Test
  public void adds_12_and_34_correctly() {
    ExpressionNode exprNode = AddNodeGen.create(
        new IntegerLiteral(12),
        new IntegerLiteral(34));
    assertEquals(46L, TestUtil.evaluate(exprNode));
  }

  @Test
  public void adding_1_to_int_max_does_not_overflow() {
    ExpressionNode exprNode = AddNodeGen.create(
        new IntegerLiteral(Long.MAX_VALUE),
        new IntegerLiteral(1));
    assertEquals(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE), TestUtil.evaluate(exprNode));
  }

  @Test
  public void adding_long_to_BigInteger_works() {
    BigInteger rndBig = new BigInteger(100, new Random());
    ExpressionNode exprNode = AddNodeGen.create(
        new BigIntegerLiteral(rndBig),
        new IntegerLiteral(5)
    );
    assertEquals(rndBig.add(BigInteger.valueOf(5)), TestUtil.evaluate(exprNode));
  }
}
