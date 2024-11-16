package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.SCOPE_SLOT;

import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.StatementNode;
import tailspin.language.runtime.TemplatesInstance;

public class DefineTypeConstraintNode extends StatementNode {
  private final TemplatesInstance templates;

  DefineTypeConstraintNode(TemplatesInstance templates, SourceSection sourceSection) {
    super(sourceSection);
    this.templates = templates;
  }

  public static DefineTypeConstraintNode create(TemplatesInstance templates,
      SourceSection sourceSection) {
    return new DefineTypeConstraintNode(templates, sourceSection);
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    MaterializedFrame definingScope = (MaterializedFrame) frame.getObjectStatic(SCOPE_SLOT);
    templates.setDefiningScope(definingScope);
  }
}
