package tailspin.language.nodes.structure;

import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import java.util.ArrayList;
import tailspin.language.runtime.Structure;

@GenerateInline
public abstract class WriteStructurePropertyNode extends Node {
  public abstract Structure executeWriteProperty(Node node, Structure target, String key, Object value);

  @Specialization(guards = {"nokey == null", "novalue == null"})
  protected Structure writeEmbedded(Structure target, @SuppressWarnings("unused") String nokey, @SuppressWarnings("unused") Object novalue) {
    return target;
  }

  @Specialization(guards = "nokey == null")
  protected Structure writeEmbedded(Structure target, @SuppressWarnings("unused") String nokey, Structure value,
      @CachedLibrary(limit = "2") @Shared DynamicObjectLibrary dynamicObjectLibrary) {
    for (Object key : dynamicObjectLibrary.getKeyArray(value)) {
      dynamicObjectLibrary.put(target, key, dynamicObjectLibrary.getOrDefault(value, key, null));
    }
    return target;
  }

  @Specialization(guards = "nokey == null")
  protected Structure writeManyEmbedded(Structure target, @SuppressWarnings("unused") String nokey, ArrayList<?> values,
      @CachedLibrary(limit = "2") @Shared DynamicObjectLibrary dynamicObjectLibrary) {
    for (Object valueObject : values) {
      Structure value = (Structure) valueObject;
      for (Object key : dynamicObjectLibrary.getKeyArray(value)) {
        dynamicObjectLibrary.put(target, key, dynamicObjectLibrary.getOrDefault(value, key, null));
      }
    }
    return target;
  }

  @Specialization(guards = "key != null")
  protected Structure writeLong(Structure target, String key, long value,
      @CachedLibrary(limit = "2") @Shared DynamicObjectLibrary dynamicObjectLibrary) {
    dynamicObjectLibrary.putLong(target, key, value);
    return target;
  }

  @Specialization(guards = "key != null")
  protected Structure writeProperty(Structure target, String key, Object value,
      @CachedLibrary(limit = "2") @Shared DynamicObjectLibrary dynamicObjectLibrary) {
    dynamicObjectLibrary.put(target, key, value);
    return target;
  }
}
