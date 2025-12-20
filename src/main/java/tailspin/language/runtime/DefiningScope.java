package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.MaterializedFrame;

public class DefiningScope {
  @CompilationFinal
  MaterializedFrame frame;

  @CompilationFinal
  DefiningScope parent;

  Object state;

  public DefiningScope(MaterializedFrame frame, DefiningScope parent) {
    this.frame = frame;
    this.parent = parent;
  }

  public MaterializedFrame getFrame() {
    return frame;
  }

  public DefiningScope getParentScope() {
    return parent;
  }

  public Object getState() {
    return state;
  }

  public void setState(Object state) {
    this.state = state;
  }
}
