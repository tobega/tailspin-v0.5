package tailspin.language.nodes.transform;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.TransformNode;
import tailspin.language.nodes.ValueNode;

@NodeChild(value = "value", type = ValueNode.class)
@NodeChild(value = "matcher", type = MatcherNode.class, executeWith = "value")
public abstract class FilterNode extends TransformNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private AppendResultNode appendResultNode = AppendResultNode.create();

  public FilterNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  @Specialization
  void doFilter(VirtualFrame frame, Object value, boolean matches) {
    if (matches) {
      appendResultNode.execute(frame, getResultSlot(), value);
    }
  }

  public static FilterNode create(ValueNode value, MatcherNode matcher, SourceSection sourceSection) {
    return FilterNodeGen.create(sourceSection, value, matcher);
  }
}
