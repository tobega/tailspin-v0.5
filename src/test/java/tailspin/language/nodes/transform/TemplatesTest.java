package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.createScopeFdb;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.array.ArrayLiteral;
import tailspin.language.nodes.iterate.RangeIteration;
import tailspin.language.nodes.iterate.ResultAggregatingNode;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.EqualityMatcherNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.numeric.SubtractNode;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.runtime.TailspinArray;
import tailspin.language.runtime.Templates;

public class TemplatesTest {
  @Test
  void simple_function() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();

    ValueNode expr1 = AddNode.create(
        IntegerLiteral.create(5),
        ReadContextValueNode.create(-1, CV_SLOT), false);
    StatementNode first = EmitNode.create(ResultAggregatingNode.create(expr1));

    ValueNode expr2 = AddNode.create(
        IntegerLiteral.create(7),
        ReadContextValueNode.create(-1, CV_SLOT), false);
    StatementNode second = EmitNode.create(ResultAggregatingNode.create(expr2));

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), createScopeFdb().build(), BlockNode.create(List.of(first, second)));
    @SuppressWarnings("unchecked")
    Iterator<Object> result = ((ArrayList<Object>) callTarget.call(null, 3L, null)).iterator();
    assertEquals(8L, result.next());
    assertEquals(10L, result.next());
    assertFalse(result.hasNext());
  }

  @Test
  void simple_matcher() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();

    MatcherNode eq3 = EqualityMatcherNode.create(null, IntegerLiteral.create(3));
    StatementNode whenEq3 = EmitNode.create(ResultAggregatingNode.create(IntegerLiteral.create(0)));

    MatcherNode alwaysTrue = new AlwaysTrueMatcherNode();
    StatementNode otherwise = EmitNode.create(ResultAggregatingNode.create(ReadContextValueNode.create(-1, CV_SLOT)));

    MatchBlockNode matchStatement = MatchBlockNode.create(List.of(
        MatchTemplateNode.create(eq3, whenEq3),
        MatchTemplateNode.create(alwaysTrue, otherwise)
    ));

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), createScopeFdb().build(), matchStatement);
    assertEquals(0L, callTarget.call(null, 3L, null));

    assertEquals(5L, callTarget.call(null, 5L, null));
  }

  @Test
  void array_chain() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int rangeSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int startSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int endSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int incrementSlot = fdb.addSlot(FrameSlotKind.Illegal, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    FrameDescriptor.Builder scopeFdb = createScopeFdb();
    int flatMapSlot = scopeFdb.addSlot(FrameSlotKind.Illegal, null, null);

    Templates flatMap = new Templates();
    // [100..1:-1
    RangeIteration backwards = RangeIteration.create(rangeSlot,
        CallDefinedTemplatesNode.create(ReadContextValueNode.create(-1, rangeSlot), ReadContextValueNode.create(0, flatMapSlot)),
        startSlot, IntegerLiteral.create(100L), true, endSlot, IntegerLiteral.create(1L), true, incrementSlot, IntegerLiteral.create(-1L));

    // -> \($! 100 - $!\)
    BlockNode flatMapBlock = BlockNode.create(List.of(
        EmitNode.create(ResultAggregatingNode.create(ReadContextValueNode.create(-1, CV_SLOT))),
        EmitNode.create(ResultAggregatingNode.create(SubtractNode.create(IntegerLiteral.create(100L), ReadContextValueNode.create(-1, CV_SLOT), false))
        )
    ));
    flatMap.setCallTarget(TemplatesRootNode.create(fdb.build(), createScopeFdb().build(), flatMapBlock));

    // ]
    ArrayLiteral input = ArrayLiteral.create(buildSlot, List.of(backwards));

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), scopeFdb.build(),
        BlockNode.create(List.of(
            DefineTemplatesNode.create(flatMap, flatMapSlot),
            EmitNode.create(ResultAggregatingNode.create(input)))
        ));
    TailspinArray result = (TailspinArray) callTarget.call(null, null, null);
    assertEquals(200, result.getArraySize());
    assertEquals(100L, result.getNative(0));
    assertEquals(0L, result.getNative(1));
    assertEquals(75L, result.getNative(50));
    assertEquals(25L, result.getNative(51));
    assertEquals(1L, result.getNative(198));
    assertEquals(99L, result.getNative(199));
  }
}
