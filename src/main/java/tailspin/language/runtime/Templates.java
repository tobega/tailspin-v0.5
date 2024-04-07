package tailspin.language.runtime;

import com.oracle.truffle.api.CallTarget;

public class Templates {
  private CallTarget callTarget;

  public CallTarget getCallTarget() {
    return callTarget;
  }

  public void setCallTarget(CallTarget callTarget) {
    this.callTarget = callTarget;
  }
}
