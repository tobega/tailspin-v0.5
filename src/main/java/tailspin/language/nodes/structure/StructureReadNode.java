package tailspin.language.nodes.structure;

import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import java.util.ArrayList;
import tailspin.language.TypeError;
import tailspin.language.nodes.ValueNode;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.VocabularyType;

@NodeChild(value = "structure", type = ValueNode.class)
public abstract class StructureReadNode extends ValueNode {
  final VocabularyType key;

  protected StructureReadNode(VocabularyType key) {
    this.key = key;
  }

  public abstract Object executeDirect(Object target);

  @Specialization(rewriteOn = UnexpectedResultException.class)
  protected long doLong(Structure target, @CachedLibrary(limit = "2") @Shared DynamicObjectLibrary dynamicObjectLibrary)
      throws UnexpectedResultException {
    return dynamicObjectLibrary.getLongOrDefault(target, key, 0L);
  }

  @Specialization
  protected Object doRead(Structure target, @CachedLibrary(limit = "2") @Shared DynamicObjectLibrary dynamicObjectLibrary) {
    return dynamicObjectLibrary.getOrDefault(target, key, null);
  }

  @Specialization
  @SuppressWarnings("unchecked")
  protected Object doMultiSelect(ArrayList<?> multiple) {
    ((ArrayList<Object>) multiple).replaceAll(this::executeDirect);
    return multiple;
  }

  @Specialization
  protected Object doIllegal(Object target) {
    throw new TypeError(String.format("Cannot read %s by %s", target.getClass(), key));
  }

  public static StructureReadNode create(ValueNode target, VocabularyType key) {
    return StructureReadNodeGen.create(key, target);
  }
}
