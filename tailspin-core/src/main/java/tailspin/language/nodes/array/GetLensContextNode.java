package tailspin.language.nodes.array;

import static tailspin.language.runtime.Templates.LENS_CONTEXT_SLOT;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.TypeError;
import tailspin.language.nodes.TailspinNode;

@GenerateInline(value = false)
public abstract class GetLensContextNode extends TailspinNode {

  GetLensContextNode(SourceSection sourceSection) {
    super(sourceSection);
  }

  public abstract Object executeGeneric(VirtualFrame frame);

  @Specialization
  public Object doGetLensContext(VirtualFrame frame) {
    Object context = frame.getObjectStatic(LENS_CONTEXT_SLOT);
    if (context == null) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new TypeError("Not in lens context", this);
    }
    return context;
  }

  public static GetLensContextNode create(SourceSection sourceSection) {
    return GetLensContextNodeGen.create(sourceSection);
  }
}
