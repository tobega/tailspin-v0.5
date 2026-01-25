package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;

public class Reference {

  public static class Slot {
    @CompilationFinal
    int slot = -1;
    @CompilationFinal
    boolean isExported;

    public void setSlot(int slot) {
      this.slot = slot;
    }

    public boolean isExported() {
      return isExported;
    }

    public Reference atLevel(int level) {
      if (level >= 0) isExported = true;
      return new Reference(level, this);
    }

    public boolean isUndefined() {
      return slot < 0;
    }
  }

  final int level;
  final Slot slot;

  private Reference(int level, Slot slot) {
    this.level = level;
    this.slot = slot;
  }

  public int getLevel() {
    if (level < 0 && slot.isExported()) return 0;
    return level;
  }

  public int getSlot() {
    return slot.slot;
  }

  public static Reference to(int level, int slot) {
    Slot holder = new Slot();
    holder.setSlot(slot);
    return holder.atLevel(level);
  }
}
