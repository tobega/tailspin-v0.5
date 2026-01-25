package tailspin.language.runtime;

import com.oracle.truffle.api.CallTarget;

public class TemplatesInstance {

  private DefiningScope definingScope;
  private final CallTarget callTarget;

  public TemplatesInstance(DefiningScope definingScope, CallTarget callTarget) {
    this.definingScope = definingScope;
    this.callTarget = callTarget;
  }

  public void setDefiningScope(DefiningScope definingScope) {
    this.definingScope = definingScope;
  }

  public DefiningScope definingScope() {
    return definingScope;
  }

  public CallTarget callTarget() {
    return callTarget;
  }
}
