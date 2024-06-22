package tailspin.language.factory;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;

public record ChainSlots(int values, int cv, int result) {


  public static ChainSlots on(FrameDescriptor.Builder builder) {
    return new ChainSlots(
        builder.addSlot(FrameSlotKind.Static, null, null),
        builder.addSlot(FrameSlotKind.Illegal, null, null),
        builder.addSlot(FrameSlotKind.Static, null, null)
    );
  }
}
