package tailspin.language.math;

import java.math.BigInteger;
import tailspin.language.ExpressionNode;
import tailspin.language.TestUtil;
import tailspin.language.literals.IntegerLiteral;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
