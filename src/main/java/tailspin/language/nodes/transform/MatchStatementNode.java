package tailspin.language.nodes.transform;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import java.util.List;
import tailspin.language.nodes.StatementNode;

public class MatchStatementNode extends StatementNode {
  @Children
  private final MatchTemplateNode[] matchTemplates;

  public MatchStatementNode(List<MatchTemplateNode> matchTemplates) {
    this.matchTemplates = matchTemplates.toArray(new MatchTemplateNode[0]);
  }

  @Override
  @ExplodeLoop
  public void executeVoid(VirtualFrame frame) {
    for (MatchTemplateNode matchTemplate : matchTemplates) {
      if (matchTemplate.executeMatcher(frame)) return;
    }
  }
}
