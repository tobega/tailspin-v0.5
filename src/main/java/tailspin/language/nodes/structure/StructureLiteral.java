package tailspin.language.nodes.structure;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.source.SourceSection;
import java.util.List;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.VocabularyType;

public class StructureLiteral extends ValueNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  ValueNode builder;

  protected StructureLiteral(Shape rootShape, List<VocabularyType> keys, List<ValueNode> values,
      SourceSection sourceSection) {
    super(sourceSection);
    ValueNode builder = NewEmptyStructureNode.create(rootShape, sourceSection);
    for (int i = 0; i < keys.size(); i++) {
      if (keys.get(i) == null) {
        builder = WriteEmbeddedStructureNode.create(builder, values.get(i), sourceSection);
      } else {
        builder = WriteKeyValueNode.create(keys.get(i), builder, values.get(i), INTERNAL_CODE_SOURCE);
      }
    }
    this.builder = builder;
  }

  public static StructureLiteral create(Shape rootShape, List<VocabularyType> keys, List<ValueNode> values,
      SourceSection sourceSection) {
    return new StructureLiteral(rootShape, keys, values, sourceSection);
  }

  @Override
  public Structure executeGeneric(VirtualFrame frame) {
    Structure s = (Structure) builder.executeGeneric(frame);
    s.needsFreeze(); // Children already created frozen
    return s;
  }
}
