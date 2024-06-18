package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.SCOPE_SLOT;

import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.StatementNode;
import tailspin.language.runtime.Templates;

public class DefineTemplatesNode extends StatementNode {
  private final Templates templates;

  private DefineTemplatesNode(Templates templates) {
    this.templates = templates;
  }

  public static DefineTemplatesNode create(Templates templates) {
    return new DefineTemplatesNode(templates);
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    templates.setDefiningScope((MaterializedFrame) frame.getObjectStatic(SCOPE_SLOT));
  }
}
