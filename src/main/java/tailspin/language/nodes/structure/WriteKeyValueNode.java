package tailspin.language.nodes.structure;

import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Structure;

@NodeChild(value = "target", type = ValueNode.class)
@NodeChild(value = "value", type = ValueNode.class)
public abstract class WriteKeyValueNode extends ValueNode {
  final String key;

  protected WriteKeyValueNode(String key) {
    this.key = key;
  }

  public static WriteKeyValueNode create(String key, ValueNode target, ValueNode value) {
    return WriteKeyValueNodeGen.create(key, target, value);
  }

  @Specialization
  Structure doLong(Structure target, long value, @CachedLibrary(limit = "1") @Shared DynamicObjectLibrary dynamicObjectLibrary) {
    dynamicObjectLibrary.putLong(target, key, value);
    return target;
  }

  @Specialization
  Structure doWrite(Structure target, Object value, @CachedLibrary(limit = "1") @Shared DynamicObjectLibrary dynamicObjectLibrary) {
    dynamicObjectLibrary.put(target, key, value);
    return target;
  }

  @Specialization
  protected Object doIllegal(Object target, Object ignored) {
    throw new TypeError(String.format("Cannot write %s by %s", target.getClass(), key));
  }
}
