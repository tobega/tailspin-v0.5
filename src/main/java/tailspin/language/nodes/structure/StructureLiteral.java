package tailspin.language.nodes.structure;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.object.Shape;
import java.util.List;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Structure;

public abstract class StructureLiteral extends ValueNode {
  final Shape rootShape;
  final String[] keys;
  @Children
  final ValueNode[] values;

  protected StructureLiteral(Shape rootShape, List<String> keys, List<ValueNode> values) {
    this.rootShape = rootShape;
    this.keys = keys.toArray(String[]::new);
    this.values = values.toArray(ValueNode[]::new);
  }

  @Specialization
  public Structure createLiteral(VirtualFrame frame,
      @Cached(inline = true) WriteStructurePropertyNode writeNode) {
    Structure result = new Structure(rootShape);
    for (int i = 0; i < keys.length; i++) {
      writeNode.executeWriteProperty(this, result, keys[i], values[i].executeGeneric(frame));
    }
    return result;
  }

  public static StructureLiteral create(Shape rootShape, List<String> keys, List<ValueNode> values) {
    return StructureLiteralNodeGen.create(rootShape, keys, values);
  }
}
