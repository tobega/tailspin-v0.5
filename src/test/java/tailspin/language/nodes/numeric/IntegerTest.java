package tailspin.language.nodes.numeric;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tailspin.language.nodes.TestUtil;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.BigNumber;

public class IntegerTest {
  @Test
  public void adds_12_and_34_correctly() {
    ValueNode exprNode = AddNode.create(
        IntegerLiteral.create(12),
        IntegerLiteral.create(34));
    assertEquals(46L, TestUtil.evaluate(exprNode));
  }

  @Test
  public void adding_1_to_int_max_does_not_overflow() {
    ValueNode exprNode = AddNode.create(
        IntegerLiteral.create(Long.MAX_VALUE),
        IntegerLiteral.create(1));
    assertEquals(new BigNumber(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE)), TestUtil.evaluate(exprNode));
  }

  @Test
  public void adding_long_to_BigInteger_works() {
    BigInteger rndBig = new BigInteger(100, new Random());
    ValueNode exprNode = AddNode.create(
        new BigIntegerLiteral(rndBig),
        IntegerLiteral.create(5)
    );
    assertEquals(new BigNumber(rndBig.add(BigInteger.valueOf(5))), TestUtil.evaluate(exprNode));
  }
}
