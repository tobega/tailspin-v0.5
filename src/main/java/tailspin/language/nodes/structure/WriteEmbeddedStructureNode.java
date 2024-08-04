package tailspin.language.nodes.structure;

import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import java.util.ArrayList;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Structure;

@NodeChild(value = "target", type = ValueNode.class)
@NodeChild(value = "value", type = ValueNode.class)
public abstract class WriteEmbeddedStructureNode extends ValueNode {

  @Specialization(guards = {"novalue == null"})
  protected Structure writeNone(Structure target, @SuppressWarnings("unused") Object novalue) {
    return target;
  }

  @Specialization
  protected Structure writeEmbedded(Structure target, Structure value,
      @CachedLibrary(limit = "2") @Shared("get") DynamicObjectLibrary getLibrary,
      @CachedLibrary(limit = "2") @Shared("put") DynamicObjectLibrary putLibrary) {
    for (Object key : getLibrary.getKeyArray(value)) {
      putLibrary.put(target, key, getLibrary.getOrDefault(value, key, null));
    }
    return target;
  }

  @Specialization
  protected Structure writeManyEmbedded(Structure target, ArrayList<?> values,
      @CachedLibrary(limit = "2") @Shared("get") DynamicObjectLibrary getLibrary,
      @CachedLibrary(limit = "2") @Shared("put") DynamicObjectLibrary putLibrary) {
    for (Object valueObject : values) {
      Structure value = (Structure) valueObject;
      for (Object key : getLibrary.getKeyArray(value)) {
        putLibrary.put(target, key, getLibrary.getOrDefault(value, key, null));
      }
    }
    return target;
  }

  public static WriteEmbeddedStructureNode create(ValueNode target, ValueNode value) {
    return WriteEmbeddedStructureNodeGen.create(target, value);
  }

}
