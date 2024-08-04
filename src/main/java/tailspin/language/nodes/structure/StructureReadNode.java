package tailspin.language.nodes.structure;

import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Structure;

@NodeChild(value = "structure", type = ValueNode.class)
public abstract class StructureReadNode extends ValueNode {
  final String key;

  protected StructureReadNode(String key) {
    this.key = key;
  }

  @Specialization(rewriteOn = UnexpectedResultException.class)
  protected long doLong(Structure target, @CachedLibrary(limit = "2") @Shared DynamicObjectLibrary dynamicObjectLibrary)
      throws UnexpectedResultException {
    return dynamicObjectLibrary.getLongOrDefault(target, key, 0);
  }

  @Specialization
  protected Object doRead(Structure target, @CachedLibrary(limit = "2") @Shared DynamicObjectLibrary dynamicObjectLibrary) {
    return dynamicObjectLibrary.getOrDefault(target, key, null);
  }

  @Specialization
  protected Object doIllegal(Object target) {
    throw new TypeError(String.format("Cannot read %s by %s", target.getClass(), key));
  }

  public static StructureReadNode create(ValueNode target, String key) {
    return StructureReadNodeGen.create(key, target);
  }
}
