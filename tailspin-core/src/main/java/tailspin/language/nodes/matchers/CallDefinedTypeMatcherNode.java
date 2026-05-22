package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;
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

  @GenerateInline(value = false)
  public static abstract class DispatchNode extends Node {
    public abstract Object executeDispatch(TemplatesInstance templates, Object currentValue, Object resultBuilder);
    @Specialization
    protected static Object dispatchDirectly(
        @SuppressWarnings("unused") TemplatesInstance templates,
        Object currentValue,
        Object resultBuilder,
        @Cached("create(templates.callTarget())") DirectCallNode directCallNode) {
      return directCallNode.call(templates.definingScope(), currentValue, resultBuilder);
    }
  }
}
