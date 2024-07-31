package tailspin.language.nodes.structure;

import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import tailspin.language.TypeError;
import tailspin.language.runtime.Structure;

@GenerateInline
public abstract class WriteStructurePropertyNode extends Node {
  public abstract Structure executeWriteProperty(Node node, Structure target, String key, Object value);

  @Specialization(limit = "2")
  protected Structure writeProperty(Structure target, String key, Object value,
      @CachedLibrary("target") InteropLibrary interopLibrary) {
    try {
      interopLibrary.writeMember(target, key, value);
    } catch (UnsupportedMessageException | UnsupportedTypeException | UnknownIdentifierException e) {
      throw new TypeError(e.getMessage());
    }
    return target;
  }
}
