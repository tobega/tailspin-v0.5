package tailspin.language.runtime;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;

public class Reference {
  @CompilationFinal
  int level = -1;
  @CompilationFinal
  int slot = -1;

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getSlot() {
    return slot;
  }

  public void setSlot(int slot) {
    this.slot = slot;
  }

  public static Reference to(int level, int slot) {
    Reference ref = new Reference();
    ref.setLevel(level);
    ref.setSlot(slot);
    return ref;
  }
}
