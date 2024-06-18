package tailspin.language.runtime;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.MaterializedFrame;

public class Templates {
  public static final int SCOPE_SLOT = 0;
  public static final int CV_SLOT = 1;
  public static final int EMIT_SLOT = 2;
  public static final int TEMP_RESULT_SLOT = 3;

  public static final int STATE_SLOT = 0;

  public static FrameDescriptor.Builder createBasicFdb() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    assertSlot(SCOPE_SLOT, fdb.addSlot(FrameSlotKind.Static, null, null));
    assertSlot(CV_SLOT, fdb.addSlot(FrameSlotKind.Illegal, null, null));
    assertSlot(EMIT_SLOT, fdb.addSlot(FrameSlotKind.Static, null, null));
    assertSlot(TEMP_RESULT_SLOT, fdb.addSlot(FrameSlotKind.Static, null, null));
    return fdb;
  }

  public static FrameDescriptor.Builder createScopeFdb() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    assertSlot(STATE_SLOT, fdb.addSlot(FrameSlotKind.Static, null, null));
    return fdb;
  }

  private static void assertSlot(int expected, int slot) {
    if (slot != expected) throw new AssertionError();
  }

  @CompilationFinal
  private CallTarget callTarget;

  private MaterializedFrame definingScope;

  public CallTarget getCallTarget() {
    return callTarget;
  }

  public void setCallTarget(CallTarget callTarget) {
    this.callTarget = callTarget;
  }

  public MaterializedFrame getDefiningScope() {
    return definingScope;
  }

  public void setDefiningScope(MaterializedFrame definingScope) {
    this.definingScope = definingScope;
  }
}
