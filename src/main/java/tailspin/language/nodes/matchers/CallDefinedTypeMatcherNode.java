package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.nodes.transform.CallDefinedTemplatesNode.DispatchNode;
import tailspin.language.runtime.TemplatesInstance;

public abstract class CallDefinedTypeMatcherNode extends MatcherNode {
  final TemplatesInstance templates;

  protected CallDefinedTypeMatcherNode(TemplatesInstance templates, SourceSection sourceSection) {
    super(sourceSection);
    this.templates = templates;
  }

  @Specialization
  public boolean doDispatch(Object toMatch,
      @Cached DispatchNode dispatchNode) {
    Object result = dispatchNode.executeDispatch(templates, toMatch, null);
    return result == toMatch;
  }

  public static CallDefinedTypeMatcherNode create(TemplatesInstance templates,
      SourceSection sourceSection) {
    return CallDefinedTypeMatcherNodeGen.create(templates, sourceSection);
  }
}
