package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.SCOPE_SLOT;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;
import tailspin.language.runtime.Templates;
import tailspin.language.runtime.TemplatesInstance;

public abstract class DefineTemplatesNode extends StatementNode {
  private final Templates templates;
  private final int slot;

  DefineTemplatesNode(Templates templates, int slot) {
    this.templates = templates;
    this.slot = slot;
  }

  public static DefineTemplatesNode create(Templates templates, int slot) {
    return DefineTemplatesNodeGen.create(templates, slot);
  }

  @Specialization
  public void doDefine(VirtualFrame frame,
      @Cached(neverDefault = true, inline = true) WriteLocalValueNode writeValue) {
    MaterializedFrame definingScope = (MaterializedFrame) frame.getObjectStatic(SCOPE_SLOT);
    writeValue.executeGeneric(definingScope, this, slot, new TemplatesInstance(definingScope, templates.getCallTarget()));
  }
}
