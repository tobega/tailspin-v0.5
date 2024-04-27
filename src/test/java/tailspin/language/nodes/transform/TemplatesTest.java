package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.interop.StopIterationException;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.literals.IntegerLiteral;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.EqualityMatcherNodeGen;
import tailspin.language.nodes.math.AddNodeGen;
import tailspin.language.nodes.value.LocalReferenceNodeGen;
import tailspin.language.runtime.ResultIterator;

public class TemplatesTest {
  @Test
  void simple_function() throws StopIterationException {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    ValueNode expr1 = AddNodeGen.create(
        new IntegerLiteral(5),
        LocalReferenceNodeGen.create(cvSlot));
    StatementNode first = new EmitNode(expr1, resultSlot);

    ValueNode expr2 = AddNodeGen.create(
        new IntegerLiteral(7),
        LocalReferenceNodeGen.create(cvSlot));
    StatementNode second = new EmitNode(expr2,resultSlot);

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), cvSlot, new BlockNode(List.of(first, second)), resultSlot);
    ResultIterator result = (ResultIterator) callTarget.call(3L);
    assertEquals(8L, result.getIteratorNextElement());
    assertEquals(10L, result.getIteratorNextElement());
    assertFalse(result.hasIteratorNextElement());
  }

  @Test
  void simple_matcher() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    MatcherNode eq3 = EqualityMatcherNodeGen.create(
        LocalReferenceNodeGen.create(cvSlot), new IntegerLiteral(3));
    StatementNode whenEq3 = new EmitNode(new IntegerLiteral(0), resultSlot);

    MatcherNode alwaysTrue = new AlwaysTrueMatcherNode();
    StatementNode otherwise = new EmitNode(LocalReferenceNodeGen.create(cvSlot), resultSlot);

    MatchStatementNode matchStatement = new MatchStatementNode(List.of(
        new MatchTemplateNode(eq3, whenEq3),
        new MatchTemplateNode(alwaysTrue, otherwise)
    ));

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), cvSlot, matchStatement, resultSlot);
    assertEquals(0L, callTarget.call(3L));

    assertEquals(5L, callTarget.call(5L));
  }
}
