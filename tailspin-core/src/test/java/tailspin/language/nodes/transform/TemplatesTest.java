package tailspin.language.nodes.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;
import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.createScopeFdb;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.source.SourceSection;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.iterate.ResultAggregatingNode;
import tailspin.language.nodes.matchers.AlwaysTrueMatcherNode;
import tailspin.language.nodes.matchers.EqualityMatcherNode;
import tailspin.language.nodes.numeric.AddNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.nodes.value.ReadContextValueNode;
import tailspin.language.runtime.Templates;
import tailspin.language.runtime.stream.ListStream;

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
    ListStream stream = (ListStream) callTarget.call(null, 3L, null);
    assertEquals(8L, stream.next());
    assertEquals(10L, stream.next());
    assertFalse(stream.hasNext());
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
}
