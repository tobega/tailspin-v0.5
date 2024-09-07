package tailspin.language.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class TransformNode extends TailspinNode {

  @CompilerDirectives.CompilationFinal
  private Integer resultSlot = null;
  public abstract void executeTransform(VirtualFrame frame);

  public int getResultSlot() {
    return resultSlot;
  }

  public void setResultSlot(int resultSlot) {
    this.resultSlot = resultSlot;
  }
}
