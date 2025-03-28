package tailspin.language.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Arrays;

@ExportLibrary(InteropLibrary.class)
public class Structure extends DynamicObject implements TruffleObject {
  private boolean isMutable = true;

  public Structure(Shape shape) {
    super(shape);
  }

  public boolean needsFreeze() {
    if (isMutable) {
      isMutable = false;
      return true;
    }
    return false;
  }

  @SuppressWarnings("unused")
  public boolean isMutable() {
    return isMutable;
  }

  public Structure getThawed() {
    if (isMutable) return this;
    DynamicObjectLibrary dol = DynamicObjectLibrary.getUncached();
    Structure thawed = new Structure(getShape().getRoot());
    for (Object key : dol.getKeyArray(this)) {
      dol.put(thawed, key, dol.getOrDefault(this, key, null));
    }
    return thawed;
  }

  @Override
  public String toString() {
    return toDisplayString(false, DynamicObjectLibrary.getUncached()).toString();
  }

  @ExportMessage
  public Object toDisplayString(@SuppressWarnings("unused") boolean allowSideEffects,
      @CachedLibrary("this") DynamicObjectLibrary thisObjectLibrary) {
    Object[] keyArray = thisObjectLibrary.getKeyArray(this);
    Object[] ordered = Arrays.copyOf(keyArray, keyArray.length);
    Arrays.sort(ordered);
    StringBuilder result = new StringBuilder("{");
    for (Object key : ordered) {
      Object value = thisObjectLibrary.getOrDefault(this, key, null);
      if (value instanceof TaggedValue(VocabularyType type, Object taggedValue) && type == key) {
        value = taggedValue;
      }
      if (value instanceof TruffleString ts) {
        value = TailspinStrings.escapeAndQuote(ts);
      }
      result.append(", ")
          .append(key).append(":").append(" ")
          .append(InteropLibrary.getUncached().toDisplayString(value));
    }
    result.append(" }").deleteCharAt(1);
    return result.toString();
  }

  @ExportMessage
  Object getMembers(@SuppressWarnings("unused") boolean includeInternal,
      @CachedLibrary("this") DynamicObjectLibrary thisObjectLibrary) {
    return TailspinArray.value(thisObjectLibrary.getKeyArray(this));
  }

  @ExportMessage
  Object readMember(String member,
      @CachedLibrary("this") DynamicObjectLibrary thisObjectLibrary) {
    Object value = thisObjectLibrary.getOrDefault(this, member, null);
    if (value == null) throw new IllegalArgumentException("No such member " + member);
    return value;
  }

  @ExportMessage
  void writeMember(String member, Object value,
      @CachedLibrary("this") DynamicObjectLibrary thisObjectLibrary) {
    thisObjectLibrary.put(this, member, value);
  }

  @ExportMessage final boolean hasMembers() { return true; }
  @ExportMessage final boolean isMemberReadable(String member) { return false; }
  @ExportMessage final boolean isMemberModifiable(String member) { return false; }
  @ExportMessage final boolean isMemberInsertable(String member) { return true; }
}
