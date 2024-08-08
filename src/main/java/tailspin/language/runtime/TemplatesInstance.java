package tailspin.language.runtime;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.MaterializedFrame;

public class TemplatesInstance {

  private MaterializedFrame definingScope;
  private final CallTarget callTarget;

  public TemplatesInstance(MaterializedFrame definingScope, CallTarget callTarget) {
    this.definingScope = definingScope;
    this.callTarget = callTarget;
  }

  public void setDefiningScope(MaterializedFrame definingScope) {
    this.definingScope = definingScope;
  }

  public MaterializedFrame definingScope() {
    return definingScope;
  }

  public CallTarget callTarget() {
    return callTarget;
  }
}
