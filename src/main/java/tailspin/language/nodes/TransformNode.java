package tailspin.language.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;

public abstract class TransformNode extends TailspinNode {

  @CompilerDirectives.CompilationFinal
  private Integer resultSlot = null;

  public TransformNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  public abstract void executeTransform(VirtualFrame frame);

  public int getResultSlot() {
    return resultSlot;
  }

  public void setResultSlot(int resultSlot) {
    this.resultSlot = resultSlot;
  }
}
