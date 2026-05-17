package tailspin.language;

import com.oracle.truffle.api.nodes.ControlFlowException;
import tailspin.language.runtime.DefiningScope;

public final class TailCallException extends ControlFlowException {
  private final Object nextValue;
  private final DefiningScope nextScope;
  private final Object resultBuilder;

  public TailCallException(Object nextValue, DefiningScope nextScope, Object resultBuilder) {
    this.nextValue = nextValue;
    this.nextScope = nextScope;
    this.resultBuilder = resultBuilder;
  }

  public Object getNextValue() { return nextValue; }
  public DefiningScope getNextScope() { return nextScope; }
  public Object getResultBuilder() { return resultBuilder; }
}