package tailspin.language.nodes.state;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;
import static tailspin.language.runtime.Templates.STATE_SLOT;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateCached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.WriteContextValueNode.WriteLocalValueNode;
import tailspin.language.runtime.Reference;

@NodeChild(value = "valueExpr", type = ValueNode.class)
@GenerateCached(alwaysInlineCached=true)
public abstract class WriteStateValueNode extends StatementNode {
  private final int level;

  protected WriteStateValueNode(int level, SourceSection sourceSection) {
    super(sourceSection);
    this.level = level;
  }

  @Specialization
  protected void writeObject(VirtualFrame frame, Object value,
      @Cached(neverDefault = true) GetStateFrameNode getFrame,
      @Cached(neverDefault = true) WriteLocalValueNode writeValue) {
    VirtualFrame contextFrame = getFrame.execute(frame, this, level);
    writeValue.executeGeneric(contextFrame, this, STATE_SLOT, value);
  }

  public static WriteStateValueNode create(int level, ValueNode valueNode) {
    return WriteStateValueNodeGen.create(level, INTERNAL_CODE_SOURCE, valueNode);
  }
}
