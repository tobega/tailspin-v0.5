package tailspin.language.nodes.structure;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.object.Shape;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Structure;

public class NewEmptyStructureNode extends ValueNode {
  final Shape rootShape;

  NewEmptyStructureNode(Shape rootShape) {
    this.rootShape = rootShape;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return new Structure(rootShape);
  }

  public static NewEmptyStructureNode create(Shape rootShape) {
    return new NewEmptyStructureNode(rootShape);
  }
}
