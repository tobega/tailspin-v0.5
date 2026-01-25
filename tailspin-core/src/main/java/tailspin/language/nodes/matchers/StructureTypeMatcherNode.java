package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.VocabularyType;

@GenerateInline(false)
public abstract class StructureTypeMatcherNode extends MatcherNode {
  final VocabularyType[] requiredKeys;
  final boolean allowExtraFields;
  final VocabularyType[] optionalKeys;

  protected StructureTypeMatcherNode(VocabularyType[] requiredKeys, boolean allowExtraFields, VocabularyType[] optionalKeys,
      SourceSection sourceSection) {
    super(sourceSection);
    this.requiredKeys = requiredKeys;
    this.allowExtraFields = allowExtraFields;
    this.optionalKeys = optionalKeys;
  }

  @Specialization(guards = "requiredKeys.length == 0")
  protected boolean isAnyStructure(Structure ignored) {
    return true;
  }

  @Specialization(guards = "requiredKeys.length > 0")
  @ExplodeLoop
  protected boolean isStructure(Structure target,
      @CachedLibrary(limit = "2") DynamicObjectLibrary dynamicObjectLibrary) {
    for (VocabularyType key : requiredKeys) {
      if (!dynamicObjectLibrary.containsKey(target, key)) return false;
    }
    if (!allowExtraFields) {
      int optionalKeysPresent = 0;
      for (VocabularyType key : optionalKeys) {
        if (dynamicObjectLibrary.containsKey(target, key))
          optionalKeysPresent++;
      }
      if (dynamicObjectLibrary.getKeyArray(target).length
          != requiredKeys.length + optionalKeysPresent)
        return false;
    }
    return true;
  }

  @Fallback
  protected boolean notStructure(Object ignored) { return false; }

  public static StructureTypeMatcherNode create(VocabularyType[] requiredKeys, boolean allowExtraFields, VocabularyType[] optionalKeys,
      SourceSection sourceSection) {
    return StructureTypeMatcherNodeGen.create(requiredKeys, allowExtraFields, optionalKeys, sourceSection);
  }
}
