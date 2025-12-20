package tailspin.language.nodes.transform;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;
import static tailspin.language.runtime.Templates.SCOPE_SLOT;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;
import tailspin.language.runtime.DefiningScope;
import tailspin.language.runtime.Templates;
import tailspin.language.runtime.TemplatesInstance;

public abstract class DefineTemplatesNode extends StatementNode {
  private final Templates templates;
  private final int slot;

  DefineTemplatesNode(Templates templates, int slot, SourceSection sourceSection) {
    super(sourceSection);
    this.templates = templates;
    this.slot = slot;
  }

  public static DefineTemplatesNode create(Templates templates, int slot) {
    return DefineTemplatesNodeGen.create(templates, slot, INTERNAL_CODE_SOURCE);
  }

  @Specialization
  public void doDefine(VirtualFrame frame,
      @Cached(neverDefault = true, inline = true) WriteLocalValueNode writeValue) {
    DefiningScope definingScope = (DefiningScope) frame.getObjectStatic(SCOPE_SLOT);
    writeValue.executeGeneric(definingScope.getFrame(), this, slot, new TemplatesInstance(definingScope, templates.getCallTarget()));
  }
}
