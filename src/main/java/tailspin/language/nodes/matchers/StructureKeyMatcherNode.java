package tailspin.language.nodes.matchers;

import com.oracle.truffle.api.dsl.GenerateInline;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.source.SourceSection;
import tailspin.language.nodes.MatcherNode;
import tailspin.language.runtime.Structure;
import tailspin.language.runtime.VocabularyType;

@GenerateInline(false)
public abstract class StructureKeyMatcherNode extends MatcherNode {
  final VocabularyType key;
  @Child
  @SuppressWarnings("FieldMayBeFinal")
  MatcherNode matcher;
  final boolean isOptional;

  protected StructureKeyMatcherNode(VocabularyType key, MatcherNode matcher, boolean isOptional,
      SourceSection sourceSection) {
    super(sourceSection);
    this.key = key;
    this.matcher = matcher;
    this.isOptional = isOptional;
  }

  @Specialization
  protected boolean doKeyMatch(VirtualFrame frame, Structure structure,
      @CachedLibrary(limit = "2") DynamicObjectLibrary dynamicObjectLibrary) {
    Object value = dynamicObjectLibrary.getOrDefault(structure, key, null);
    if (value == null) return isOptional;
    return matcher.executeMatcherGeneric(frame, value);
  }

  public static StructureKeyMatcherNode create(VocabularyType key, MatcherNode matcher, boolean isOptional,
      SourceSection sourceSection) {
    return StructureKeyMatcherNodeGen.create(key, matcher, isOptional, sourceSection);
  }
}
