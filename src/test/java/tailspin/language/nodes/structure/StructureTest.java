package tailspin.language.nodes.structure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.source.SourceSection;
import java.util.List;
import org.junit.jupiter.api.Test;
import tailspin.language.nodes.TestUtil;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.numeric.IntegerLiteral;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.TaggedValue;
import tailspin.language.runtime.VocabularyType;

public class StructureTest {
  SourceSection sourceSection = INTERNAL_CODE_SOURCE;
  public final Shape rootShape = Shape.newBuilder().build();
  @Test
  void create() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    ValueNode structureNode = StructureLiteral.create(rootShape, List.of(new VocabularyType("foo"), new VocabularyType("bar")), List.of(IntegerLiteral.create(3L,
        sourceSection), IntegerLiteral.create(5L, sourceSection)), sourceSection);
    Structure created = (Structure) TestUtil.evaluate(structureNode, fdb.build(), List.of());
    assertEquals("{ bar: 5, foo: 3 }", created.toDisplayString(false, DynamicObjectLibrary.getUncached()));
  }

  @Test
  void read() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    VocabularyType fooType = new VocabularyType("foo");
    ValueNode structureNode = StructureLiteral.create(rootShape, List.of(fooType, new VocabularyType("bar")), List.of(IntegerLiteral.create(3L,
        sourceSection), IntegerLiteral.create(5L, sourceSection)), sourceSection);
    ValueNode readNode = StructureReadNode.create(structureNode, fooType, sourceSection);
    TaggedValue foo = (TaggedValue) TestUtil.evaluate(readNode, fdb.build(), List.of());
    assertEquals(3L, foo.value());
  }
}
