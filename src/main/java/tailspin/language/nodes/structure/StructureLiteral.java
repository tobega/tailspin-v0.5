package tailspin.language.nodes.structure;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.object.Shape;
import java.util.List;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.VocabularyType;

public class StructureLiteral extends ValueNode {
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  ValueNode builder;

  protected StructureLiteral(Shape rootShape, List<VocabularyType> keys, List<ValueNode> values) {
    ValueNode builder = NewEmptyStructureNode.create(rootShape);
    for (int i = 0; i < keys.size(); i++) {
      if (keys.get(i) == null) {
        builder = WriteEmbeddedStructureNode.create(builder, values.get(i));
      } else {
        builder = WriteKeyValueNode.create(keys.get(i), builder, values.get(i));
      }
    }
    this.builder = builder;
  }

  public static StructureLiteral create(Shape rootShape, List<VocabularyType> keys, List<ValueNode> values) {
    return new StructureLiteral(rootShape, keys, values);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return builder.executeGeneric(frame);
  }
}
