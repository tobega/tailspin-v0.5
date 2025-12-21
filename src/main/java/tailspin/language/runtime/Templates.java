package tailspin.language.runtime;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;

public class Templates {
  public static final int SCOPE_SLOT = 0;
  public static final int CV_SLOT = 1;
  public static final int EMIT_SLOT = 2;
  public static final int LENS_CONTEXT_SLOT = 3;

  public static FrameDescriptor.Builder createBasicFdb() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    assertSlot(SCOPE_SLOT, fdb.addSlot(FrameSlotKind.Static, null, null));
    assertSlot(CV_SLOT, fdb.addSlot(FrameSlotKind.Static, null, null));
    assertSlot(EMIT_SLOT, fdb.addSlot(FrameSlotKind.Static, null, null));
    assertSlot(LENS_CONTEXT_SLOT, fdb.addSlot(FrameSlotKind.Static, null, null));
    return fdb;
  }

  public static FrameDescriptor.Builder createScopeFdb() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    return fdb;
  }

  private static void assertSlot(int expected, int slot) {
    if (slot != expected) throw new AssertionError();
  }

  @CompilationFinal
  private CallTarget callTarget;

  @CompilationFinal
  private boolean needsScope = false;

  @CompilationFinal
  private boolean auxiliary = false;

  @CompilationFinal
  private int definitionLevel = -100;

  private String type;

  public CallTarget getCallTarget() {
    return callTarget;
  }

  public void setCallTarget(CallTarget callTarget) {
    this.callTarget = callTarget;
  }

  public void setNeedsScope() {
    needsScope = true;
  }

  public boolean needsScope() {
    return needsScope;
  }

  public void setDefinitionLevel(int definitionLevel) {
    this.definitionLevel = definitionLevel;
  }

  public int getDefinitionLevel() {
    return definitionLevel;
  }

  public void setType(String templateType) {
    type = templateType;
  }

  public String getType() {
    return type;
  }

  public void setAuxiliary() {
    auxiliary = true;
  }

  public boolean isAuxiliary() {
    return auxiliary;
  }
}
