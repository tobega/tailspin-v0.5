package tailspin.language.runtime;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;

public class Templates {
  public static final int CV_SLOT = 0;
  public static final int RESULT_SLOT = 1;

  public static FrameDescriptor.Builder createBasicFdb() {
    FrameDescriptor.Builder fdb = FrameDescriptor.newBuilder();
    assertSlot(CV_SLOT, fdb.addSlot(FrameSlotKind.Illegal, null, null));
    assertSlot(RESULT_SLOT, fdb.addSlot(FrameSlotKind.Static, null, null));
    return fdb;
  }

  private static void assertSlot(int expected, int slot) {
    if (slot != expected) throw new AssertionError();
  }

  private CallTarget callTarget;

  public CallTarget getCallTarget() {
    return callTarget;
  }

  public void setCallTarget(CallTarget callTarget) {
    this.callTarget = callTarget;
  }
}
