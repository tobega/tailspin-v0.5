package tailspin.language.nodes;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;

public abstract class TailspinNode extends Node {

  private final SourceSection sourceSection;

  protected TailspinNode(SourceSection sourceSection) {
    this.sourceSection = sourceSection;
  }

  @Override
  public SourceSection getSourceSection() {
    return sourceSection;
  }
}
