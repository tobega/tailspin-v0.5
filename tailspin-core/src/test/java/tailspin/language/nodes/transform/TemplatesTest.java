package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;
import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.createScopeFdb;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.source.SourceSection;
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
  SourceSection sourceSection = INTERNAL_CODE_SOURCE;
  @Test
  void simple_function() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();

    ValueNode expr1 = AddNode.create(
        IntegerLiteral.create(5, sourceSection),
        ReadContextValueNode.create(-1, CV_SLOT), false, sourceSection);
    StatementNode first = EmitNode.create(ResultAggregatingNode.create(expr1),
        sourceSection);

    ValueNode expr2 = AddNode.create(
        IntegerLiteral.create(7, sourceSection),
        ReadContextValueNode.create(-1, CV_SLOT), false, sourceSection);
    StatementNode second = EmitNode.create(ResultAggregatingNode.create(expr2),
        sourceSection);

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), createScopeFdb().build(), BlockNode.create(List.of(first, second),
            sourceSection),
        sourceSection);
    @SuppressWarnings("unchecked")
    Iterator<Object> result = ((ArrayList<Object>) callTarget.call(null, 3L, null)).iterator();
    assertEquals(8L, result.next());
    assertEquals(10L, result.next());
    assertFalse(result.hasNext());
  }

  @Test
  void simple_matcher() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();

    MatcherNode eq3 = EqualityMatcherNode.create(false, IntegerLiteral.create(3, sourceSection),
        sourceSection);
    StatementNode whenEq3 = EmitNode.create(ResultAggregatingNode.create(IntegerLiteral.create(0,
            sourceSection)
    ), sourceSection);

    MatcherNode alwaysTrue = new AlwaysTrueMatcherNode(sourceSection);
    StatementNode otherwise = EmitNode.create(ResultAggregatingNode.create(ReadContextValueNode.create(-1, CV_SLOT)
    ), sourceSection);

    MatchBlockNode matchStatement = MatchBlockNode.create(List.of(
        MatchTemplateNode.create(eq3, whenEq3, sourceSection),
        MatchTemplateNode.create(alwaysTrue, otherwise, sourceSection)
    ), sourceSection);

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), createScopeFdb().build(), matchStatement,
        sourceSection);
    assertEquals(0L, callTarget.call(null, 3L, null));

    assertEquals(5L, callTarget.call(null, 5L, null));
  }

  @Test
  void array_chain() {
    FrameDescriptor.Builder fdb = Templates.createBasicFdb();
    int rangeSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int startSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int endSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int incrementSlot = fdb.addSlot(FrameSlotKind.Static, null, null);
    int buildSlot = fdb.addSlot(FrameSlotKind.Static, null, null);

    FrameDescriptor.Builder scopeFdb = createScopeFdb();
    int flatMapSlot = scopeFdb.addSlot(FrameSlotKind.Static, null, null);

    Templates flatMap = new Templates();
    // [100..1:-1
    RangeIteration backwards = RangeIteration.create(rangeSlot,
        CallDefinedTemplatesNode.create(ReadContextValueNode.create(-1, rangeSlot), ReadContextValueNode.create(0, flatMapSlot)),
        startSlot, IntegerLiteral.create(100L,
            sourceSection), true, endSlot, IntegerLiteral.create(1L, sourceSection), true, incrementSlot, IntegerLiteral.create(-1L,
            sourceSection), sourceSection);

    // -> \($! 100 - $!\)
    BlockNode flatMapBlock = BlockNode.create(List.of(
        EmitNode.create(ResultAggregatingNode.create(ReadContextValueNode.create(-1, CV_SLOT)
        ), sourceSection),
        EmitNode.create(ResultAggregatingNode.create(SubtractNode.create(IntegerLiteral.create(100L,
                sourceSection), ReadContextValueNode.create(-1, CV_SLOT), false, sourceSection)
            ),
            sourceSection)
    ), sourceSection);
    flatMap.setCallTarget(TemplatesRootNode.create(fdb.build(), createScopeFdb().build(), flatMapBlock,
        sourceSection));

    // ]
    ArrayLiteral input = ArrayLiteral.create(buildSlot, List.of(backwards), sourceSection);

    CallTarget callTarget = TemplatesRootNode.create(fdb.build(), scopeFdb.build(),
        BlockNode.create(List.of(
            DefineTemplatesNode.create(flatMap, flatMapSlot),
            EmitNode.create(ResultAggregatingNode.create(input), sourceSection)),
            sourceSection), sourceSection);
    TailspinArray result = (TailspinArray) callTarget.call(null, null, null);
    assertEquals(200, result.getArraySize());
    assertEquals(100L, result.getNative(0, false));
    assertEquals(0L, result.getNative(1, false));
    assertEquals(75L, result.getNative(50, false));
    assertEquals(25L, result.getNative(51, false));
    assertEquals(1L, result.getNative(198, false));
    assertEquals(99L, result.getNative(199, false));
  }
}
