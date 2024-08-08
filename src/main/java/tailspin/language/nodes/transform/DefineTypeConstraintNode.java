package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.SCOPE_SLOT;

import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.StatementNode;
import tailspin.language.runtime.TemplatesInstance;

public class DefineTypeConstraintNode extends StatementNode {
  private final TemplatesInstance templates;

  DefineTypeConstraintNode(TemplatesInstance templates) {
    this.templates = templates;
  }

  public static DefineTypeConstraintNode create(TemplatesInstance templates) {
    return new DefineTypeConstraintNode(templates);
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    MaterializedFrame definingScope = (MaterializedFrame) frame.getObjectStatic(SCOPE_SLOT);
    templates.setDefiningScope(definingScope);
  }
}
