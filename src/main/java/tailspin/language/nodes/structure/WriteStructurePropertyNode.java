package tailspin.language.nodes.structure;

import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import tailspin.language.runtime.Structure;

@GenerateInline
public abstract class WriteStructurePropertyNode extends Node {
  public abstract Structure executeWriteProperty(Node node, Structure target, String key, Object value);

  @Specialization
  protected Structure writeLong(Structure target, String key, long value,
      @CachedLibrary(limit = "2") @Shared DynamicObjectLibrary dynamicObjectLibrary) {
    dynamicObjectLibrary.putLong(target, key, value);
    return target;
  }

  @Specialization
  protected Structure writeProperty(Structure target, String key, Object value,
      @CachedLibrary(limit = "2") @Shared DynamicObjectLibrary dynamicObjectLibrary) {
    dynamicObjectLibrary.put(target, key, value);
    return target;
  }
}
