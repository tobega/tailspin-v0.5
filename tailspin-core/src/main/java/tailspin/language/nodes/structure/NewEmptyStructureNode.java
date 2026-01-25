package tailspin.language.nodes.structure;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Structure;

public class NewEmptyStructureNode extends ValueNode {
  final Shape rootShape;

  NewEmptyStructureNode(Shape rootShape, SourceSection sourceSection) {
    super(sourceSection);
    this.rootShape = rootShape;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return new Structure(rootShape);
  }

  public static NewEmptyStructureNode create(Shape rootShape, SourceSection sourceSection) {
    return new NewEmptyStructureNode(rootShape, sourceSection);
  }
}
