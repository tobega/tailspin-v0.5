package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.CV_SLOT;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import java.util.List;
import tailspin.language.nodes.StatementNode;
import tailspin.language.nodes.ValueNode;
import tailspin.language.nodes.value.ReadContextValueNode;

public abstract class MatchBlockNode extends StatementNode {
  @Child
  @Executed
  @SuppressWarnings("FieldMayBeFinal")
  ValueNode toMatchNode;

  @Children
  private final MatchTemplateNode[] matchTemplates;

  MatchBlockNode(ValueNode toMatchNode, List<MatchTemplateNode> matchTemplates) {
    this.toMatchNode = toMatchNode;
    this.matchTemplates = matchTemplates.toArray(new MatchTemplateNode[0]);
  }

  @Specialization
  @ExplodeLoop
  public void doLong(VirtualFrame frame, long toMatch) {
    for (MatchTemplateNode matchTemplate : matchTemplates) {
      if (matchTemplate.executeLong(frame, toMatch)) return;
    }
  }

  @Specialization
  @ExplodeLoop
  public void doObject(VirtualFrame frame, Object toMatch) {
    for (MatchTemplateNode matchTemplate : matchTemplates) {
      if (matchTemplate.executeGeneric(frame, toMatch)) return;
    }
  }

  public static MatchBlockNode create(List<MatchTemplateNode> matchTemplates) {
    return MatchBlockNodeGen.create(ReadContextValueNode.create(-1, CV_SLOT), matchTemplates);
  }
}
