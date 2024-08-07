package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.VocabularyType;

@GenerateInline(false)
public abstract class StructureTypeMatcherNode extends MatcherNode {
  final VocabularyType[] requiredKeys;
  final boolean allowExtraFields;

  protected StructureTypeMatcherNode(VocabularyType[] requiredKeys, boolean allowExtraFields) {
    this.requiredKeys = requiredKeys;
    this.allowExtraFields = allowExtraFields;
  }

  @Specialization(guards = "requiredKeys.length == 0")
  protected boolean isAnyStructure(Structure ignored) {
    return true;
  }

  @Specialization(guards = "requiredKeys.length > 0")
  @ExplodeLoop
  protected boolean isStructure(Structure target,
      @CachedLibrary(limit = "2") DynamicObjectLibrary dynamicObjectLibrary) {
    if (!allowExtraFields && dynamicObjectLibrary.getKeyArray(target).length != requiredKeys.length) return false;
    for (VocabularyType key : requiredKeys) {
      if (!dynamicObjectLibrary.containsKey(target, key)) return false;
    }
    return true;
  }

  @Fallback
  protected boolean notStructure(Object ignored) { return false; }

  public static StructureTypeMatcherNode create(VocabularyType[] requiredKeys, boolean allowExtraFields) {
    return StructureTypeMatcherNodeGen.create(requiredKeys, allowExtraFields);
  }
}
