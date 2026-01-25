package tailspin.language.nodes.state;

import static tailspin.language.TailspinLanguage.INTERNAL_CODE_SOURCE;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateCached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.DefiningScope;

@GenerateCached(alwaysInlineCached=true)
public abstract class ReadStateValueNode extends ValueNode {
  private final int level;

  protected ReadStateValueNode(int level, SourceSection sourceSection) {
    super(sourceSection);
    this.level = level;
  }

  @Specialization
  protected Object readObject(VirtualFrame frame,
      @Cached(neverDefault = true) GetDefiningScopeNode getScope) {
    DefiningScope scope = getScope.execute(frame, this, level);
    return scope.getState();
  }

  public static ReadStateValueNode create(int level) {
    return ReadStateValueNodeGen.create(level, INTERNAL_CODE_SOURCE);
  }
}

