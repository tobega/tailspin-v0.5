package tailspin.language.nodes.iterate;

import com.oracle.truffle.api.nodes.ControlFlowException;

public class NotSingleResultException extends ControlFlowException {

  private final Object previousResult;

  public NotSingleResultException(Object previousResult) {
    this.previousResult = previousResult;
  }

  public Object getPreviousResult() {
    return previousResult;
  }
}
