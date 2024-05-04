package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.RESULT_SLOT;

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
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.numeric.RangeLiteral;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.EqualityMatcherNodeGen;
import tailspin.language.nodes.numeric.AddNodeGen;
import tailspin.language.nodes.numeric.SubtractNode;
import tailspin.language.nodes.value.LocalReferenceNode;
import tailspin.language.runtime.ResultIterator;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.Templates;

public class TemplatesTest {
  @Test
  void simple_function() throws StopIterationException {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();

    ValueNode expr1 = AddNodeGen.create(
        IntegerLiteral.create(5),
        LocalReferenceNode.create(CV_SLOT));
    StatementNode first = EmitNode.create(expr1, RESULT_SLOT);

    ValueNode expr2 = AddNodeGen.create(
        IntegerLiteral.create(7),
        LocalReferenceNode.create(CV_SLOT));
    StatementNode second = EmitNode.create(expr2,RESULT_SLOT);

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
    StatementNode whenEq3 = EmitNode.create(IntegerLiteral.create(0), RESULT_SLOT);

    MatcherNode alwaysTrue = new AlwaysTrueMatcherNode();
    StatementNode otherwise = EmitNode.create(LocalReferenceNode.create(CV_SLOT), RESULT_SLOT);

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
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    // [100..1:-1
    RangeLiteral backwards = RangeLiteral.create(IntegerLiteral.create(100L), IntegerLiteral.create(1L), IntegerLiteral.create(-1L));

    // -> \($! 100 - $!\)
    Templates flatMap = new Templates();
    BlockNode flatMapBlock = BlockNode.create(List.of(
        EmitNode.create(LocalReferenceNode.create(CV_SLOT), RESULT_SLOT),
        EmitNode.create(SubtractNode.create(IntegerLiteral.create(100L), LocalReferenceNode.create(CV_SLOT)), RESULT_SLOT)
    ));
    flatMap.setCallTarget(TemplatesRootNode.create(fdb.build(), flatMapBlock));

    ChainNode arrayContents = ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot, List.of(
        backwards,
        SendToTemplatesNode.create(chainCvSlot, flatMap, 0)
    ));
    // ]
    ArrayLiteral input = ArrayLiteral.create(List.of(arrayContents));

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), EmitNode.create(input, RESULT_SLOT));
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
