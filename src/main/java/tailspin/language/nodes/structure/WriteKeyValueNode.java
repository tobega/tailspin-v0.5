package tailspin.language.nodes.structure;

import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.VocabularyType;

@NodeChild(value = "target", type = ValueNode.class)
@NodeChild(value = "value", type = ValueNode.class)
public abstract class WriteKeyValueNode extends ValueNode {
  final VocabularyType type;

  protected WriteKeyValueNode(VocabularyType type) {
    this.type = type;
  }

  public static WriteKeyValueNode create(VocabularyType key, ValueNode target, ValueNode value) {
    return WriteKeyValueNodeGen.create(key, target, value);
  }

  @Specialization
  Structure doLong(Structure target, long value, @CachedLibrary(limit = "1") @Shared DynamicObjectLibrary dynamicObjectLibrary) {
    dynamicObjectLibrary.putLong(target, type, value);
    return target;
  }

  @Specialization
  Structure doWrite(Structure target, Object value, @CachedLibrary(limit = "1") @Shared DynamicObjectLibrary dynamicObjectLibrary) {
    dynamicObjectLibrary.put(target, type, value);
    return target;
  }

  @Specialization
  protected Object doIllegal(Object target, Object ignored) {
    throw new TypeError(String.format("Cannot write %s by %s", target.getClass(), type));
  }
}
