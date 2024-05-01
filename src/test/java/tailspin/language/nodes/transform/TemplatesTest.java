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
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    ValueNode expr1 = AddNodeGen.create(
        IntegerLiteral.create(5),
        LocalReferenceNode.create(cvSlot));
    StatementNode first = EmitNode.create(expr1, resultSlot);

    ValueNode expr2 = AddNodeGen.create(
        IntegerLiteral.create(7),
        LocalReferenceNode.create(cvSlot));
    StatementNode second = EmitNode.create(expr2,resultSlot);

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), cvSlot, BlockNode.create(List.of(first, second)), resultSlot);
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
        LocalReferenceNode.create(cvSlot), IntegerLiteral.create(3));
    StatementNode whenEq3 = EmitNode.create(IntegerLiteral.create(0), resultSlot);

    MatcherNode alwaysTrue = new AlwaysTrueMatcherNode();
    StatementNode otherwise = EmitNode.create(LocalReferenceNode.create(cvSlot), resultSlot);

    MatchStatementNode matchStatement = new MatchStatementNode(List.of(
        new MatchTemplateNode(eq3, whenEq3),
        new MatchTemplateNode(alwaysTrue, otherwise)
    ));

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), cvSlot, matchStatement, resultSlot);
    assertEquals(0L, callTarget.call(3L));

    assertEquals(5L, callTarget.call(5L));
  }

  @Test
  void array_chain() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    int cvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int resultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainValuesSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int chainCvSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int chainResultSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    // [100..1:-1
    RangeLiteral backwards = RangeLiteral.create(IntegerLiteral.create(100L), IntegerLiteral.create(1L), IntegerLiteral.create(-1L));

    // -> \($! 100 - $!\)
    Templates flatMap = new Templates();
    BlockNode flatMapBlock = BlockNode.create(List.of(
        EmitNode.create(LocalReferenceNode.create(cvSlot), resultSlot),
        EmitNode.create(SubtractNode.create(IntegerLiteral.create(100L), LocalReferenceNode.create(cvSlot)), resultSlot)
    ));
    flatMap.setCallTarget(TemplatesRootNode.create(fdb.build(), cvSlot, flatMapBlock, resultSlot));

    ChainNode arrayContents = ChainNode.create(chainValuesSlot, chainCvSlot, chainResultSlot, List.of(
        backwards,
        SendToTemplatesNode.create(chainCvSlot, flatMap)
    ));
    // ]
    ArrayLiteral input = ArrayLiteral.create(List.of(arrayContents));

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), cvSlot, EmitNode.create(input, resultSlot), resultSlot);
    TailspinArray result = (TailspinArray) callTarget.call(0);
    assertEquals(200, result.getArraySize());
    assertEquals(100L, result.getNative(0));
    assertEquals(0L, result.getNative(1));
    assertEquals(75L, result.getNative(50));
    assertEquals(25L, result.getNative(51));
    assertEquals(1L, result.getNative(198));
    assertEquals(99L, result.getNative(199));
  }
}
