package tailspin.language.nodes.transform;

import static tailspin.language.runtime.Templates.CV_SLOT;
import static tailspin.language.runtime.Templates.EMIT_SLOT;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.TailCallException;
import tailspin.language.nodes.StatementNode;

public class TailRecursiveLoopNode extends StatementNode {

  @Child private LoopNode loopNode;

  public TailRecursiveLoopNode(StatementNode templateBody, FrameDescriptor scopeDescriptor) {
    super(templateBody.getSourceSection());
    this.loopNode = Truffle.getRuntime().createLoopNode(new TemplateLoopDriver(templateBody, scopeDescriptor, templateBody.getSourceSection()));
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    loopNode.execute(frame);
  }

  private static class TemplateLoopDriver extends Node implements RepeatingNode {
    @Child private StatementNode body;
    @Child private TemplatesRootNode.CreateScopeNode createScope;

    TemplateLoopDriver(StatementNode body, FrameDescriptor scopeDescriptor, SourceSection sourceSection) {
      this.body = body;
      this.createScope = TemplatesRootNode.CreateScopeNode.create(scopeDescriptor, sourceSection);
    }

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
      try {
        body.executeVoid(frame);
        return false;
      } catch (TailCallException e) {
        frame.setObjectStatic(CV_SLOT, e.getNextValue());
        createScope.executeVoid(frame);
        frame.setObjectStatic(EMIT_SLOT, e.getResultBuilder());

        return true;
      }
    }
  }
}