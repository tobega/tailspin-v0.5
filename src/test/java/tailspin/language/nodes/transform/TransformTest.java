package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import tailspin.language.TestUtil;
import tailspin.language.TypeError;
import tailspin.language.nodes.ExpressionNode;
import tailspin.language.nodes.literals.IntegerLiteral;
import tailspin.language.nodes.math.AddNodeGen;

public class TransformTest {

  @Test
  void adds_current_value() {
    ExpressionNode exprNode = AddNodeGen.create(
        new IntegerLiteral(12),
        new CurrentValueReferenceNode());
    assertEquals(46L, TestUtil.evaluate(exprNode, 34L));
  }

  @Test
  void reference_null_current_value_blows_up() {
    ExpressionNode exprNode = AddNodeGen.create(
        new IntegerLiteral(12),
        new CurrentValueReferenceNode());
    assertThrows(TypeError.class, () -> TestUtil.evaluate(exprNode, null, null));
  }

  @Test
  void expression_transform() {
    ExpressionTransformNode exprTr = new ExpressionTransformNode(
        AddNodeGen.create(
            new IntegerLiteral(12),
            new CurrentValueReferenceNode())
    );
    @SuppressWarnings("unchecked")
    Queue<Object> result = (Queue<Object>) TestUtil.run(exprTr, 23L, new ArrayDeque<>());
    assertEquals(1, result.size());
    assertEquals(35L, result.poll());
  }
}
