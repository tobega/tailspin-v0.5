package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tailspin.language.runtime.Templates.CV_SLOT;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.interop.StopIterationException;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.array.ArrayLiteral;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.EqualityMatcherNodeGen;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.iterate.RangeIteration;
import tailspin.language.nodes.numeric.SubtractNode;
import tailspin.language.nodes.value.LocalReferenceNode;
import tailspin.language.runtime.ResultIterator;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.Templates;

public class TemplatesTest {
  @Test
  void simple_function() throws StopIterationException {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();

    ValueNode expr1 = AddNode.create(
        IntegerLiteral.create(5),
        LocalReferenceNode.create(CV_SLOT));
    StatementNode first = EmitNode.create(expr1);

    ValueNode expr2 = AddNode.create(
        IntegerLiteral.create(7),
        LocalReferenceNode.create(CV_SLOT));
    StatementNode second = EmitNode.create(expr2);

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), BlockNode.create(List.of(first, second)));
    ResultIterator result = (ResultIterator) callTarget.call(3L);
    assertEquals(8L, result.getIteratorNextElement());
    assertEquals(10L, result.getIteratorNextElement());
    assertFalse(result.hasIteratorNextElement());
  }

  @Test
  void simple_matcher() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();

    MatcherNode eq3 = EqualityMatcherNodeGen.create(
        LocalReferenceNode.create(CV_SLOT), IntegerLiteral.create(3));
    StatementNode whenEq3 = EmitNode.create(IntegerLiteral.create(0));

    MatcherNode alwaysTrue = new AlwaysTrueMatcherNode();
    StatementNode otherwise = EmitNode.create(LocalReferenceNode.create(CV_SLOT));

    MatchStatementNode matchStatement = MatchStatementNode.create(List.of(
        MatchTemplateNode.create(eq3, whenEq3),
        MatchTemplateNode.create(alwaysTrue, otherwise)
    ));

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), matchStatement);
    assertEquals(0L, callTarget.call(3L));

    assertEquals(5L, callTarget.call(5L));
  }

  @Test
  void array_chain() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int rangeSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    Templates flatMap = new Templates();
    // [100..1:-1
    RangeIteration backwards = RangeIteration.create(rangeSlot, SendToTemplatesNode.create(rangeSlot, flatMap, 0), resultSlot, IntegerLiteral.create(100L), IntegerLiteral.create(1L), IntegerLiteral.create(-1L));

    // -> \($! 100 - $!\)
    BlockNode flatMapBlock = BlockNode.create(List.of(
        EmitNode.create(LocalReferenceNode.create(CV_SLOT)),
        EmitNode.create(SubtractNode.create(IntegerLiteral.create(100L), LocalReferenceNode.create(CV_SLOT))
        )
    ));
    flatMap.setCallTarget(TemplatesRootNode.create(fdb.build(), flatMapBlock));

    // ]
    ArrayLiteral input = ArrayLiteral.create(List.of(backwards));

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), EmitNode.create(input));
    TailspinArray result = (TailspinArray) callTarget.call((Object) null);
    assertEquals(200, result.getArraySize());
    assertEquals(100L, result.getNative(0));
    assertEquals(0L, result.getNative(1));
    assertEquals(75L, result.getNative(50));
    assertEquals(25L, result.getNative(51));
    assertEquals(1L, result.getNative(198));
    assertEquals(99L, result.getNative(199));
  }
}
